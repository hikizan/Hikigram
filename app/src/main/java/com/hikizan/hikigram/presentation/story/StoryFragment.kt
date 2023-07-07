package com.hikizan.hikigram.presentation.story

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanFragmentBase
import com.hikizan.hikigram.databinding.FragmentStoryBinding
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.presentation.story.adapter.StoryAdapter
import com.hikizan.hikigram.presentation.view_model.StoryViewModel
import com.hikizan.hikigram.utils.ext.showDefaultState
import com.hikizan.hikigram.utils.ext.showEmptyState
import com.hikizan.hikigram.utils.ext.showErrorState
import com.hikizan.hikigram.utils.ext.showLoadingState
import com.hikizan.hikigram.utils.ext.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryFragment : HikizanFragmentBase<FragmentStoryBinding>() {

    private val storyViewModel: StoryViewModel by viewModel()

    override fun initViewBinding(): FragmentStoryBinding {
        return FragmentStoryBinding.inflate(layoutInflater)
    }

    private val storyAdapter = StoryAdapter()

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
                setHasFixedSize(true)
                adapter = storyAdapter
            }
        }

    }

    override fun initAction() {
        binding?.apply {
            swipeStoryContainer.setOnRefreshListener {
                storyViewModel.fetchStories()
                swipeStoryContainer.isRefreshing = false
            }

            storyAdapter.onItemClick = { selectedData ->
                requireContext().showToast(selectedData.name)
                DetailStoryActivity.start(requireContext(), selectedData.id)
            }
        }
    }

    override fun initProcess() {
        storyViewModel.fetchStories()
    }

    override fun initObservers() {
        binding?.apply {
            storyViewModel.storiesResult.observe(viewLifecycleOwner) { stories ->
                when (stories) {
                    is State.Loading -> {
                        msvStories.showLoadingState()
                    }
                    is State.Success -> {
                        msvStories.showDefaultState()
                        storyAdapter.setData(stories.data)
                    }
                    is State.Error -> {
                        msvStories.showErrorState(
                            message = stories.message ?: getString(R.string.message_error_state),
                            action = Pair(getString(R.string.action_retry)) {
                                storyViewModel.fetchStories()
                            }
                        )
                    }
                    is State.Empty -> {
                        msvStories.showEmptyState()
                    }
                }
            }
        }
    }
}