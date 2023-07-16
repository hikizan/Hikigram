package com.hikizan.hikigram.data.story.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hikizan.hikigram.data.local.datastore.UserPreference
import com.hikizan.hikigram.data.remote.model.response.StoryResult
import com.hikizan.hikigram.data.remote.network.ApiClient
import com.hikizan.hikigram.utils.constants.AppConstants
import com.hikizan.hikigram.utils.ext.setBearer
import kotlinx.coroutines.flow.first
import timber.log.Timber

class StoryPagingSource(
    private val apiClient: ApiClient,
    private val userPreference: UserPreference
) : PagingSource<Int, StoryResult>() {

    override fun getRefreshKey(state: PagingState<Int, StoryResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResult> {
        return try {
            val page = params.key ?: AppConstants.INITIAL_PAGE_INDEX
            Timber.d("login token = ${userPreference.getLoginToken().first().setBearer()}")
            val responseData = apiClient.getStories(
                userPreference.getLoginToken().first().setBearer(),
                page,
                params.loadSize,
                AppConstants.LOCATION_FALSE
            ).listStory

            LoadResult.Page(
                data = responseData.orEmpty(),
                prevKey = if (page == 1) null else page -1,
                nextKey = if (responseData.isNullOrEmpty()) null else page +1
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}