package com.hikizan.hikigram.domain.story

import androidx.paging.PagingData
import androidx.paging.map
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.domain.story.mapper.mapToStoryDomain
import com.hikizan.hikigram.domain.story.model.request.StoryCreatedParam
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.domain.story.model.response.StoryCreated
import com.hikizan.hikigram.domain.story.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoryInteractor(private val repository: StoryRepository) : StoryUseCase {

    override fun createStory(storyCreatedParam: StoryCreatedParam): Flow<State<StoryCreated>> {
        return repository.createStory(storyCreatedParam)
    }

    override fun getStoriesWithLocation(): Flow<State<List<Story>>> {
        return repository.getStoriesWithLocation()
    }

    override fun getStoriesWithoutLocation(): Flow<PagingData<Story>> {
        return repository.getStoriesWithoutLocation().map { pagingData ->
            pagingData.map { it.mapToStoryDomain() }
        }
    }

    override fun getDetailStory(storyId: String): Flow<State<Story>> {
        return repository.getDetailStory(storyId)
    }
}