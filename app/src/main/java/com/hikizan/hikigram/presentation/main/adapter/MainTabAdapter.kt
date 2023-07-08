package com.hikizan.hikigram.presentation.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hikizan.hikigram.presentation.profile.ProfileFragment
import com.hikizan.hikigram.presentation.story.StoryFragment

class MainTabAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            StoryFragment()
        } else {
            ProfileFragment()
        }
    }
}