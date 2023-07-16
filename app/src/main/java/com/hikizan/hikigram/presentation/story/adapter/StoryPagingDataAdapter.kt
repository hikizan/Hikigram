package com.hikizan.hikigram.presentation.story.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hikizan.hikigram.databinding.ItemStoryListBinding
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.utils.ext.hikigramDateFormat
import com.hikizan.hikigram.utils.ext.orEmptyString

class StoryPagingDataAdapter :
    PagingDataAdapter<Story, StoryPagingDataAdapter.StoryPagingDataViewHolder>(
        DIFF_CALLBACK
    ) {

    var onItemClick: ((Story, ActivityOptionsCompat) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryPagingDataViewHolder {
        val binding = ItemStoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryPagingDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryPagingDataViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class StoryPagingDataViewHolder(private val binding: ItemStoryListBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun bind(data: Story) = with(binding) {
            Glide.with(itemView.context)
                .load(data.photoUrl)
                .into(imgItemPhoto)
            tvItemName.text = data.name
            tvItemDescription.text = data.description
            tvItemDate.text = data.createdAt.orEmptyString().hikigramDateFormat()

            itemView.setOnClickListener {
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.imgItemPhoto, "photo"),
                    Pair(binding.tvItemDescription, "description"),
                    Pair(binding.tvItemDate, "date"),
                    Pair(binding.tvItemName, "username")
                )
                onItemClick?.invoke(
                    data, optionsCompat
                )
            }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
