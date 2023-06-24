package com.hikizan.hikigram.domain.membership.mapper

import com.hikizan.hikigram.data.remote.model.request.RegisterRequest
import com.hikizan.hikigram.data.remote.model.response.BaseResponse
import com.hikizan.hikigram.data.remote.model.response.LoginResult
import com.hikizan.hikigram.domain.membership.model.request.RegisterParam
import com.hikizan.hikigram.domain.membership.model.response.Login
import com.hikizan.hikigram.domain.membership.model.response.Register

fun RegisterParam.mapToRegisterRequest() =
    RegisterRequest(
        name = name,
        email = email,
        password = password
    )

fun BaseResponse.mapToRegisterDomain() =
    Register(
        error = error ?: false,
        message = message
    )

fun LoginResult.mapToLoginDomain() =
    Login(
        name = name,
        token = token,
        userId = userId
    )