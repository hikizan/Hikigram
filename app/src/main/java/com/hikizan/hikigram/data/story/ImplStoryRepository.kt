package com.hikizan.hikigram.data.story

import com.hikizan.hikigram.data.local.datastore.UserPreference
import com.hikizan.hikigram.data.remote.model.request.StoriesRequest
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.domain.story.mapper.mapToRequest
import com.hikizan.hikigram.domain.story.mapper.mapToStoryDomain
import com.hikizan.hikigram.domain.story.model.request.StoryCreatedParam
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.domain.story.model.response.StoryCreated
import com.hikizan.hikigram.domain.story.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ImplStoryRepository(
    private val remoteStoryDataSource: RemoteStoryDataSource,
    private val userPreference: UserPreference
) : StoryRepository {

    override fun createStory(storyCreatedParam: StoryCreatedParam): Flow<State<StoryCreated>> {
        return flow {
            emit(State.Loading())
            try {
                val storyCreatedResponse = remoteStoryDataSource.createStory(
                    userPreference.getLoginToken().first(),
                    storyCreatedParam.mapToRequest()
                )

                emit(State.Success(
                    storyCreatedResponse.mapToStoryDomain()
                ))
            } catch (e: Exception) {
                emit(State.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getStories(): Flow<State<List<Story>>> {
        return flow {
            emit(State.Loading())
            try {
                val getStoriesResponse = remoteStoryDataSource.getStories(
                    userPreference.getLoginToken().first(),
                    StoriesRequest(location = 0)
                )

                if (getStoriesResponse.listStory != null) {
                    emit(State.Success(
                        getStoriesResponse.listStory.map {
                            it.mapToStoryDomain()
                        }
                    ))
                } else {
                    emit(State.Empty)
                }
            } catch (e: Exception) {
                emit(State.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getDetailStory(storyId: String): Flow<State<Story>> {
        return flow {
            emit(State.Loading())
            try {
                val getDetailStoryResponse = remoteStoryDataSource.getDetailStory(
                    userPreference.getLoginToken().first(),
                    storyId
                )

                if (getDetailStoryResponse.story != null) {
                    emit(State.Success(
                        getDetailStoryResponse.story.mapToStoryDomain()
                    ))
                } else {
                    emit(State.Empty)
                }
            } catch (e: Exception) {
                emit(State.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }
}