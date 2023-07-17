package com.hikizan.hikigram.presentation.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.hikizan.hikigram.domain.story.StoryUseCase
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.presentation.story.adapter.StoryPagingDataAdapter
import com.hikizan.hikigram.utils.DataDummy
import com.hikizan.hikigram.utils.MainDispatcherRule
import com.hikizan.hikigram.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/** Skenario Tes
 * Ketika berhasil memuat data.
    - Memastikan data tidak null.
    - Memastikan jumlah data sesuai dengan yang diharapkan.
    - Memastikan data pertama yang dikembalikan sesuai.

 * Ketika tidak ada data.
    - Memastikan jumlah data yang dikembalikan nol.
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyUseCase: StoryUseCase

    @Test
    fun `When getStoriesWithoutLocation should not null and return data`() = runTest {
        val dummyStories: List<Story> = DataDummy.generateDummyStoryResponse()
        val data: PagingData<Story> = PagingData.from(dummyStories)
        val expectedStories: MutableLiveData<PagingData<Story>> = MutableLiveData<PagingData<Story>>()
        expectedStories.value = data
        Mockito.`when`(storyUseCase.getStoriesWithoutLocation()).thenReturn(flowOf(data))

        val storyViewModel = StoryViewModel(storyUseCase)
        storyViewModel.fetchStoriesWithoutLocation()
        val actualStories: PagingData<Story> = storyViewModel.storiesWithoutLocationResult.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryPagingDataAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStories)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStories.size, differ.snapshot().size)
        assertEquals(dummyStories[0], differ.snapshot()[0])
    }

    @Test
    fun `When getStoriesWithoutLocation empty should return no data`() = runTest {
        val data: PagingData<Story> = PagingData.from(emptyList())
        val expectedStories: MutableLiveData<PagingData<Story>> = MutableLiveData<PagingData<Story>>()
        expectedStories.value = data
        Mockito.`when`(storyUseCase.getStoriesWithoutLocation()).thenReturn(flowOf(data))

        val storyViewModel = StoryViewModel(storyUseCase)
        storyViewModel.fetchStoriesWithoutLocation()
        val actualStories: PagingData<Story> = storyViewModel.storiesWithoutLocationResult.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryPagingDataAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStories)

        assertEquals(0, differ.snapshot().size)
    }

}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}

    override fun onRemoved(position: Int, count: Int) {}

    override fun onMoved(fromPosition: Int, toPosition: Int) {}

    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}