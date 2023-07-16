package com.hikizan.hikigram.presentation.story

import android.os.Bundle
import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanFragmentBase
import com.hikizan.hikigram.databinding.FragmentStoryBinding
import com.hikizan.hikigram.presentation.story.adapter.StoryPagingDataAdapter
import com.hikizan.hikigram.presentation.story.adapter.StoryPagingLoadingStateAdapter
import com.hikizan.hikigram.presentation.view_model.StoryViewModel
import com.hikizan.hikigram.utils.ext.showDefaultState
import com.hikizan.hikigram.utils.ext.showEmptyState
import com.hikizan.hikigram.utils.ext.showErrorState
import com.hikizan.hikigram.utils.ext.showLoadingState
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class StoryFragment : HikizanFragmentBase<FragmentStoryBinding>() {

    private val storyViewModel: StoryViewModel by viewModel()

    override fun initViewBinding(): FragmentStoryBinding {
        return FragmentStoryBinding.inflate(layoutInflater)
    }

    private val storyPagingDataAdapter = StoryPagingDataAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            initIntent()
            initUI()
            initAction()
            initProcess()
            initObservers()
        }
    }

    override fun initIntent() {
    }

    override fun initUI() {
        binding?.apply {
            swipeStoryContainer.setColorSchemeColors(
                requireContext().getColor(R.color.purple_500)
            )

            rvStories.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = storyPagingDataAdapter.withLoadStateFooter(
                    footer = StoryPagingLoadingStateAdapter {
                        storyPagingDataAdapter.retry()
                    }
                )
            }
        }
    }

    override fun initAction() {
        binding?.apply {
            swipeStoryContainer.setOnRefreshListener {
                storyViewModel.fetchStoriesWithoutLocation()
                swipeStoryContainer.isRefreshing = false
            }

            storyPagingDataAdapter.onItemClick = { selectedData, optionCompat ->
                DetailStoryActivity.start(
                    requireContext(),
                    selectedData.id,
                    optionCompat
                )
            }
        }
    }

    override fun initProcess() {
        storyViewModel.fetchStoriesWithoutLocation()
    }

    override fun initObservers() {
        binding?.apply {

            storyViewModel.storiesWithoutLocationResult.observe(viewLifecycleOwner) { pagingStories ->
                Timber.d("initObservers: pagingStories = ${storyPagingDataAdapter.snapshot().items}")
                storyPagingDataAdapter.submitData(lifecycle, pagingStories)
            }

            storyPagingDataAdapter.addLoadStateListener {
                when {
                    it.source.prepend is LoadState.Loading || it.source.refresh is LoadState.Loading -> {
                        Timber.d("addLoadStateListener: Loading")
                        msvStories.showLoadingState()
                    }

                    it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && storyPagingDataAdapter.itemCount < 1 -> {
                        msvStories.showEmptyState()
                    }

                    it.source.refresh is LoadState.NotLoading && storyPagingDataAdapter.itemCount > 1 -> {
                        Timber.d("addLoadStateListener: Show content")
                        msvStories.showDefaultState()
                    }

                    else -> {
                        msvStories.showErrorState(
                            message = getString(R.string.message_error_state),
                            action = Pair(getString(R.string.action_retry)) {
                                storyViewModel.fetchStoriesWithoutLocation()
                            }
                        )
                    }
                }
            }
        }
    }
}