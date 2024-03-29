package com.hikizan.hikigram.domain.membership

import com.hikizan.hikigram.domain.membership.model.request.LoginParam
import com.hikizan.hikigram.domain.membership.model.request.RegisterParam
import com.hikizan.hikigram.domain.membership.model.response.Login
import com.hikizan.hikigram.domain.membership.model.response.Register
import com.hikizan.hikigram.domain.reuseable.State
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun registerUser(registerParam: RegisterParam): Flow<State<Register>>
    fun loginUser(loginParam: LoginParam): Flow<State<Login>>
    fun getLoginState(): Flow<Boolean>
    fun getLoginName(): Flow<String>
    suspend fun logoutUser()
}