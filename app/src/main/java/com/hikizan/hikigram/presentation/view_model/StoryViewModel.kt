package com.hikizan.hikigram.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.domain.story.StoryUseCase
import com.hikizan.hikigram.domain.story.model.request.StoryCreatedParam
import com.hikizan.hikigram.domain.story.model.response.Location
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.domain.story.model.response.StoryCreated
import com.hikizan.hikigram.utils.proceed
import kotlinx.coroutines.launch
import java.io.File

class StoryViewModel(private val storyUseCase: StoryUseCase) : ViewModel() {

    private val _storiesResult: MutableLiveData<State<List<Story>>> = MutableLiveData()
    val storiesResult: LiveData<State<List<Story>>> get() = _storiesResult

    private val _storiesWithoutLocationResult: MutableLiveData<PagingData<Story>> = MutableLiveData()
    val storiesWithoutLocationResult get() = _storiesWithoutLocationResult

    private val _detailStoryResult: MutableLiveData<State<Story>> = MutableLiveData()
    val detailStoryResult: LiveData<State<Story>> get() = _detailStoryResult

    private val _createStoryResult: MutableLiveData<State<StoryCreated>> = MutableLiveData()
    val createStoryResult: LiveData<State<StoryCreated>> get() = _createStoryResult

    fun fetchStories() = viewModelScope.launch {
        proceed(_storiesResult) {
            storyUseCase.getStoriesWithLocation()
        }
    }

    fun fetchStoriesWithoutLocation() = viewModelScope.launch {
        storyUseCase.getStoriesWithoutLocation().cachedIn(viewModelScope).collect { pagingStory ->
            _storiesWithoutLocationResult.value = pagingStory
        }
    }

    fun fetchDetailStory(storyId: String) = viewModelScope.launch {
        proceed(_detailStoryResult) {
            storyUseCase.getDetailStory(storyId)
        }
    }

    fun createStory(
        description: String,
        photo: File,
        location: Location? = null
    ) = viewModelScope.launch {
        proceed(_createStoryResult) {
            storyUseCase.createStory(
                StoryCreatedParam(
                    description, photo, location
                )
            )
        }
    }
}