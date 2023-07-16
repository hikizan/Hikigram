package com.hikizan.hikigram.domain.story

import androidx.paging.PagingData
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.domain.story.model.request.StoryCreatedParam
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.domain.story.model.response.StoryCreated
import kotlinx.coroutines.flow.Flow

interface StoryUseCase {
    fun createStory(storyCreatedParam: StoryCreatedParam): Flow<State<StoryCreated>>
    fun getStoriesWithLocation(): Flow<State<List<Story>>>
    fun getStoriesWithoutLocation(): Flow<PagingData<Story>>
    fun getDetailStory(storyId: String): Flow<State<Story>>
}