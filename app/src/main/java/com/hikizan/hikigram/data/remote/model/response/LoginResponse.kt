package com.hikizan.hikigram.data.remote.model.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("loginResult")
    val loginResult: LoginResult?,
    @SerializedName("message")
    val message: String?
)