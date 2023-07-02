package com.hikizan.hikigram.domain.story

import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.domain.story.model.request.StoryCreatedParam
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.domain.story.model.response.StoryCreated
import com.hikizan.hikigram.domain.story.repository.StoryRepository
import kotlinx.coroutines.flow.Flow

class StoryInteractor(private val repository: StoryRepository) : StoryUseCase {

    override fun createStory(storyCreatedParam: StoryCreatedParam): Flow<State<StoryCreated>> {
        return repository.createStory(storyCreatedParam)
    }

    override fun getStories(): Flow<State<List<Story>>> {
        return repository.getStories()
    }

    override fun getDetailStory(storyId: String): Flow<State<Story>> {
        return repository.getDetailStory(storyId)
    }
}