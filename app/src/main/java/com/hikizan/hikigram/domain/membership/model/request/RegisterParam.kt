package com.hikizan.hikigram.domain.membership.model.request

data class RegisterParam(
    val name: String,
    val email: String,
    val password: String
)
