package com.hikizan.hikigram.data.membership

import com.hikizan.hikigram.data.remote.model.request.RegisterRequest
import com.hikizan.hikigram.data.remote.model.response.BaseResponse
import com.hikizan.hikigram.data.remote.model.response.LoginResponse
import com.hikizan.hikigram.data.remote.network.ApiClient

class RemoteAuthDataSource(private val apiClient: ApiClient) {

    suspend fun registerUser(registerRequest: RegisterRequest): BaseResponse {
        return apiClient.registerUser(registerRequest)
    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiClient.loginUser(email, password)
    }
}