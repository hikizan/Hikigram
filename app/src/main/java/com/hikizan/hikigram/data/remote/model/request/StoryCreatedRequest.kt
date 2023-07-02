package com.hikizan.hikigram.data.remote.model.request

import com.hikizan.hikigram.domain.story.model.response.Location
import com.hikizan.hikigram.utils.ext.emptyString
import java.io.File

data class StoryCreatedRequest(
    val description: String = emptyString,
    val photo: File,
    val location: Location? = null
)
