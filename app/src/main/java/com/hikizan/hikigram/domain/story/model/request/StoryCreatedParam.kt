package com.hikizan.hikigram.domain.story.model.request

import com.hikizan.hikigram.domain.story.model.response.Location
import java.io.File

data class StoryCreatedParam(
    val description: String,
    val photo: File,
    val location: Location? = null
)
