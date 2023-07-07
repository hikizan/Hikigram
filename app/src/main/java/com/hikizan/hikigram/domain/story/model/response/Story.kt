package com.hikizan.hikigram.domain.story.model.response

import com.hikizan.hikigram.utils.ext.emptyString

data class Story(
    val id: String,
    val name: String = emptyString,
    val description: String = emptyString,
    val photoUrl: String = emptyString,
    val createdAt: String = emptyString,
    val lat: Double? = null,
    val lng: Double? = null
)
