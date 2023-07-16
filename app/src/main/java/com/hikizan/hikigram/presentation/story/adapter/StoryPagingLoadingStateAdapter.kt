package com.hikizan.hikigram.presentation.story.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hikizan.hikigram.R
import com.hikizan.hikigram.databinding.ItemStoryPagingLoadingBinding
import com.hikizan.hikigram.utils.ext.showDefaultState
import com.hikizan.hikigram.utils.ext.showErrorState

class StoryPagingLoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<StoryPagingLoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_story_paging_loading, parent, false
            )
        )

    inner class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemStoryPagingLoadingBinding.bind(itemView)

        fun bind(loadState: LoadState) = with(binding) {
            when(loadState) {
                is LoadState.Loading -> {
                    msvItemLoadingStoryPaging.showDefaultState()
                }
                is LoadState.Error -> {
                    msvItemLoadingStoryPaging.showErrorState(
                        message = loadState.error.localizedMessage ?: itemView.context.getString(
                            R.string.message_error_state
                        ),
                        action = Pair(itemView.context.getString(R.string.action_retry)) {
                            retry.invoke()
                        }
                    )
                }
                else -> {
                    msvItemLoadingStoryPaging.showErrorState(
                        message = itemView.context.getString(
                            R.string.message_error_state
                        ),
                        action = Pair(itemView.context.getString(R.string.action_retry)) {
                            retry.invoke()
                        }
                    )
                }
            }
        }
    }
}