package com.hikizan.hikigram.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityMainBinding
import com.hikizan.hikigram.presentation.MainActivityViewModel
import com.hikizan.hikigram.presentation.main.adapter.MainTabAdapter
import com.hikizan.hikigram.presentation.membership.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : HikizanActivityBase<ActivityMainBinding>() {

    companion object {

        fun startNewTask(context: Context) {
            context.startActivity(
                Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            )
        }
    }

    private val viewPagerAdapter: MainTabAdapter by lazy {
        MainTabAdapter(
            supportFragmentManager,
            lifecycle
        )
    }

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initIntent()
        initUI()
        initAction()
        initProcess()
        initObservers()
    }

    override fun initIntent() {
    }

    override fun initUI() {
        binding?.apply {
            val tabTitles = listOf(
                "Journey Story",
                "Profile"
            )
            vpMain.apply {
                adapter = viewPagerAdapter
            }
            TabLayoutMediator(tabMain, vpMain) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    override fun initAction() {
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }
}