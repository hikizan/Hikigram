package com.hikizan.hikigram.data.membership

import com.hikizan.hikigram.data.local.datastore.UserPreference
import com.hikizan.hikigram.domain.membership.repository.AuthRepository
import com.hikizan.hikigram.domain.membership.mapper.mapToLoginDomain
import com.hikizan.hikigram.domain.membership.mapper.mapToRegisterDomain
import com.hikizan.hikigram.domain.membership.mapper.mapToRegisterRequest
import com.hikizan.hikigram.domain.membership.model.request.LoginParam
import com.hikizan.hikigram.domain.membership.model.request.RegisterParam
import com.hikizan.hikigram.domain.membership.model.response.Login
import com.hikizan.hikigram.domain.membership.model.response.Register
import com.hikizan.hikigram.domain.reuseable.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ImplAuthRepository(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val userPreference: UserPreference
) : AuthRepository {

    override fun registerUser(registerParam: RegisterParam): Flow<State<Register>> {
        return flow {
            emit(State.Loading())
            try {
                val registerResponse = remoteAuthDataSource
                    .registerUser(registerParam.mapToRegisterRequest())

                emit(State.Success(
                    registerResponse.mapToRegisterDomain()
                ))
            } catch (e: Exception) {
                emit(State.Error(e.message))
            }

        }.flowOn(Dispatchers.IO)
    }

    override fun loginUser(loginParam: LoginParam): Flow<State<Login>> {
        return flow {
            emit(State.Loading())
            try {
                val loginResponse = remoteAuthDataSource
                    .loginUser(loginParam.email, loginParam.password)

                if (loginResponse.loginResult != null) {
                    emit(State.Success(
                        loginResponse.loginResult.mapToLoginDomain()
                    ))
                    userPreference.saveLoginState(
                        isLogin = true,
                        token = loginResponse.loginResult.token.toString(),
                        userName = loginResponse.loginResult.name.toString()
                    )
                } else {
                    emit(State.Empty)
                }
            } catch (e: Exception) {
                emit(State.Error(e.message))
            }

        }.flowOn(Dispatchers.IO)
    }

    override fun getLoginState(): Flow<Boolean> {
        return userPreference.getLoginState()
    }

    override fun getLoginName(): Flow<String> {
        return userPreference.getLoginName()
    }

    override suspend fun logoutUser() {
        return userPreference.saveLoginState(isLogin = false)
    }
}