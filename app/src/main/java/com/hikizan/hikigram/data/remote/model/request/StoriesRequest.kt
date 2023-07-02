package com.hikizan.hikigram.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class StoriesRequest(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("location")
    val location: Int? = 0
)
