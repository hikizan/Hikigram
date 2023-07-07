package com.hikizan.hikigram.presentation.story

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityDetailStoryBinding
import com.hikizan.hikigram.domain.reuseable.State
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.presentation.view_model.StoryViewModel
import com.hikizan.hikigram.utils.constants.BundleKeys
import com.hikizan.hikigram.utils.ext.emptyString
import com.hikizan.hikigram.utils.ext.hikigramDateFormat
import com.hikizan.hikigram.utils.ext.orEmptyString
import com.hikizan.hikigram.utils.ext.setupHikizanToolbar
import com.hikizan.hikigram.utils.ext.showDefaultState
import com.hikizan.hikigram.utils.ext.showEmptyState
import com.hikizan.hikigram.utils.ext.showErrorState
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailStoryActivity : HikizanActivityBase<ActivityDetailStoryBinding>() {

    companion object {

        fun start(context: Context, storyId: String) {
            context.startActivity(
                Intent(context, DetailStoryActivity::class.java).apply {
                    putExtra(BundleKeys.KEY_STORY_ID, storyId)
                }
            )
        }
    }

    private val storyViewModel: StoryViewModel by viewModel()

    override fun initViewBinding(): ActivityDetailStoryBinding {
        return ActivityDetailStoryBinding.inflate(layoutInflater)
    }

    private var storyId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initIntent()
        initUI()
        initAction()
        initProcess()
        initObservers()
    }

    override fun initIntent() {
        storyId = intent.getStringExtra(BundleKeys.KEY_STORY_ID)
    }

    override fun initUI() {
        binding?.apply {
            setupHikizanToolbar(
                toolbarLayout = layoutToolbarDetailStory,
                title = storyId.orEmptyString(),
                isChild = true
            )
        }
    }

    override fun initAction() {
    }

    override fun initProcess() {
        storyViewModel.fetchDetailStory(storyId.orEmptyString())
    }

    override fun initObservers() {
        binding?.apply {
            storyViewModel.detailStoryResult.observe(this@DetailStoryActivity) { story ->
                when (story) {
                    is State.Loading -> {
                        showLoading()
                    }
                    is State.Success -> {
                        hideLoading()
                        msvStory.showDefaultState()
                        setDetailValue(story.data)
                    }
                    is State.Error -> {
                        hideLoading()
                        msvStory.showErrorState(
                            message = story.message ?: getString(R.string.message_error_state),
                            action = Pair(getString(R.string.action_retry)) {
                                storyViewModel.fetchDetailStory(storyId.orEmptyString())
                            }
                        )
                    }
                    is State.Empty -> {
                        hideLoading()
                        msvStory.showEmptyState()
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setDetailValue(story: Story) = binding?.apply {
        setupHikizanToolbar(
            toolbarLayout = layoutToolbarDetailStory,
            title = story.name,
            isChild = true
        )
        Glide.with(this@DetailStoryActivity)
            .load(story.photoUrl)
            .into(imgItemPhoto)

        tvItemDescription.text = story.description
        tvItemDate.text = story.createdAt
            .orEmptyString()
            .hikigramDateFormat()
    }
}