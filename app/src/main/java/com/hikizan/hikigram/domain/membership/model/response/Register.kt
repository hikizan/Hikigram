package com.hikizan.hikigram.domain.membership.model.response

data class Register(
    val error: Boolean = false,
    val message: String? = null
)
