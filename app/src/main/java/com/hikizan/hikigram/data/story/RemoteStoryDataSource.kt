package com.hikizan.hikigram.data.story

import com.hikizan.hikigram.data.remote.model.request.StoriesRequest
import com.hikizan.hikigram.data.remote.model.request.StoryCreatedRequest
import com.hikizan.hikigram.data.remote.model.response.BaseResponse
import com.hikizan.hikigram.data.remote.model.response.DetailStoryResponse
import com.hikizan.hikigram.data.remote.model.response.StoriesResponse
import com.hikizan.hikigram.data.remote.network.ApiClient
import com.hikizan.hikigram.utils.ext.setBearer
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class RemoteStoryDataSource(private val apiClient: ApiClient) {

    suspend fun createStory(loginToken: String, story: StoryCreatedRequest): BaseResponse {
        return if (story.location != null ) {
            apiClient.createStoryWithLocation(
                loginToken = loginToken.setBearer(),
                description = story.description.toRequestBody("text/plain".toMediaType()),
                photo = MultipartBody.Part.createFormData("photo", story.photo.name, story.photo.asRequestBody("image/jpeg".toMediaTypeOrNull())),
                latitude = story.location.latitude.toString().toRequestBody("text/plain".toMediaType()),
                longitude = story.location.longitude.toString().toRequestBody("text/plain".toMediaType())
            )
        } else {
            apiClient.createStory(
                loginToken = loginToken.setBearer(),
                description = story.description.toRequestBody("text/plain".toMediaType()),
                photo = MultipartBody.Part.createFormData("photo", story.photo.name, story.photo.asRequestBody("image/jpeg".toMediaTypeOrNull()))
            )
        }
    }

    suspend fun getStories(loginToken: String, storiesRequest: StoriesRequest): StoriesResponse {
        return apiClient.getStories(
            loginToken.setBearer(),
            storiesRequest.page,
            storiesRequest.size,
            storiesRequest.location
        )
    }

    suspend fun getDetailStory(loginToken: String, storyId: String): DetailStoryResponse {
        return apiClient.getDetailStory(loginToken.setBearer(), storyId)
    }
}