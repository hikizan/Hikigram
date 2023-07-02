package com.hikizan.hikigram.domain.story.mapper

import com.hikizan.hikigram.data.remote.model.request.StoryCreatedRequest
import com.hikizan.hikigram.data.remote.model.response.BaseResponse
import com.hikizan.hikigram.data.remote.model.response.StoryResult
import com.hikizan.hikigram.domain.story.model.request.StoryCreatedParam
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.domain.story.model.response.StoryCreated
import com.hikizan.hikigram.utils.ext.orEmptyString

fun StoryCreatedParam.mapToRequest() =
    StoryCreatedRequest(
        description = description.orEmptyString(),
        photo = photo,
        location = location
    )

fun BaseResponse.mapToStoryDomain() =
    StoryCreated(
        error = error ?: false,
        message = message.orEmptyString()
    )

fun StoryResult.mapToStoryDomain() =
    Story(
        id = id.orEmptyString(),
        name = name.orEmptyString(),
        description = description.orEmptyString(),
        photoUrl = photoUrl.orEmptyString(),
        createdAt = createdAt.orEmptyString(),
        lat = lat,
        lng = lon
    )