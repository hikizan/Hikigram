package com.hikizan.hikigram.data.remote.network

import com.hikizan.hikigram.data.remote.model.request.RegisterRequest
import com.hikizan.hikigram.data.remote.model.response.BaseResponse
import com.hikizan.hikigram.data.remote.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiClient {

    @POST("register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): BaseResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}