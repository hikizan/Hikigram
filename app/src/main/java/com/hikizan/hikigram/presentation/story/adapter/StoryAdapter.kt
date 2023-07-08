package com.hikizan.hikigram.presentation.story.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hikizan.hikigram.R
import com.hikizan.hikigram.databinding.ItemStoryListBinding
import com.hikizan.hikigram.domain.story.model.response.Story
import com.hikizan.hikigram.utils.ext.hikigramDateFormat
import com.hikizan.hikigram.utils.ext.orEmptyString

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private var listData = ArrayList<Story>()
    var onItemClick: ((Story, ActivityOptionsCompat) -> Unit)? = null

    fun setData(newListData: List<Story>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder =
        StoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_story_list, parent, false)
        )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemStoryListBinding.bind(itemView)

        fun bind(data: Story) = with(binding) {
            Glide.with(itemView.context)
                .load(data.photoUrl)
                .into(imgItemPhoto)
            tvItemName.text = data.name
            tvItemDescription.text = data.description
            tvItemDate.text = data.createdAt.orEmptyString().hikigramDateFormat()
        }
        init {
            binding.root.setOnClickListener {

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.imgItemPhoto, "photo"),
                    Pair(binding.tvItemDescription, "description"),
                    Pair(binding.tvItemDate, "date"),
                    Pair(binding.tvItemName, "username")
                )
                onItemClick?.invoke(
                    listData[adapterPosition],
                    optionsCompat
                )
            }
        }
    }
}