package com.hikizan.hikigram.domain.membership.model.response

data class Login(
    val name: String? = null,
    val token: String? = null,
    val userId: String? = null
)
