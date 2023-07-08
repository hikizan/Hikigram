package com.hikizan.hikigram.domain.story.model.response

import com.hikizan.hikigram.utils.ext.emptyString

data class StoryCreated(
    val error: Boolean?,
    val message: String? = emptyString
)
