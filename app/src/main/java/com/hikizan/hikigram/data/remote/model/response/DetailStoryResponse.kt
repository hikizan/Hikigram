package com.hikizan.hikigram.data.remote.model.response


import com.google.gson.annotations.SerializedName

data class DetailStoryResponse(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("story")
    val story: StoryResult?
)