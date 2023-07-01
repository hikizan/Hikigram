package com.hikizan.hikigram.domain.membership

import com.hikizan.hikigram.domain.membership.model.request.LoginParam
import com.hikizan.hikigram.domain.membership.model.request.RegisterParam
import com.hikizan.hikigram.domain.membership.model.response.Login
import com.hikizan.hikigram.domain.membership.model.response.Register
import com.hikizan.hikigram.domain.reuseable.State
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val repository: AuthRepository) : AuthUseCase {

    override fun registerUser(registerParam: RegisterParam): Flow<State<Register>> {
        return repository.registerUser(registerParam)
    }

    override fun loginUser(loginParam: LoginParam): Flow<State<Login>> {
        return repository.loginUser(loginParam)
    }

    override fun getLoginState(): Flow<Boolean> {
        return repository.getLoginState()
    }

    override fun getLoginName(): Flow<String> {
        return repository.getLoginName()
    }

    override suspend fun logoutUser() {
        return repository.logoutUser()
    }
}