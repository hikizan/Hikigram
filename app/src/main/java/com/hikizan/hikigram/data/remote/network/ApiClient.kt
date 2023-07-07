package com.hikizan.hikigram.data.remote.network

import com.hikizan.hikigram.data.remote.model.request.RegisterRequest
import com.hikizan.hikigram.data.remote.model.request.StoriesRequest
import com.hikizan.hikigram.data.remote.model.response.BaseResponse
import com.hikizan.hikigram.data.remote.model.response.DetailStoryResponse
import com.hikizan.hikigram.data.remote.model.response.LoginResponse
import com.hikizan.hikigram.data.remote.model.response.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @Multipart
    @POST("stories")
    suspend fun createStory(
        @Header("Authorization") loginToken: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("lat") latitude: RequestBody?,
        @Part("lon") longitude: RequestBody?
    ): BaseResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") loginToken: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int?
    ): StoriesResponse

    @GET("stories/{story_id}")
    suspend fun getDetailStory(
        @Header("Authorization") loginToken: String,
        @Path("story_id") storyId: String
    ): DetailStoryResponse
}