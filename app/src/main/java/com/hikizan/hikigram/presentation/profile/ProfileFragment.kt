package com.hikizan.hikigram.presentation.profile

import android.os.Bundle
import android.view.View
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanFragmentBase
import com.hikizan.hikigram.databinding.FragmentProfileBinding
import com.hikizan.hikigram.presentation.membership.LoginActivity
import com.hikizan.hikigram.presentation.story.CreateStoryActivity
import com.hikizan.hikigram.presentation.story.map.MapStoriesActivity
import com.hikizan.hikigram.presentation.view_model.ProfileViewModel
import com.hikizan.hikigram.utils.ext.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : HikizanFragmentBase<FragmentProfileBinding>() {

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun initViewBinding(): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater)
    }

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
    }

    override fun initAction() {
        binding?.apply {
            /*imgBookmarks.setOnClickListener {
                requireContext().showToast(getString(R.string.coming_soon))
            }*/
            imgOpenStoriesMap.setOnClickListener {
                MapStoriesActivity.start(requireContext())
            }
            btnLogout.setOnClickListener {
                profileViewModel.logoutUser()
                LoginActivity.startNewTask(requireContext())
            }
            btnCreateStory.setOnClickListener {
                CreateStoryActivity.start(requireContext())
            }
        }
    }

    override fun initProcess() {
        profileViewModel.getLoginName()
    }

    override fun initObservers() {
        binding?.apply {
            profileViewModel.loginNameResult.observe(requireActivity()) { userName ->
                tvUserName.text = getString(R.string.happy_coding, userName)
            }
        }
    }
}