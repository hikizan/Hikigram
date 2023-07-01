package com.hikizan.hikigram.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityMainBinding
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
        fun start(context: Context) {
            context.startActivity(
                Intent(context, MainActivity::class.java)
            )
        }
    }

    private val mainActivityViewModel: MainActivityViewModel by viewModel()

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
    }

    override fun initAction() {
        binding?.apply {
            btnLogout.setOnClickListener {
                mainActivityViewModel.logoutUser()
                LoginActivity.startNewTask(this@MainActivity)
            }
        }
    }

    override fun initProcess() {
        mainActivityViewModel.apply {
            getLoginState()
            getLoginName()
        }
    }

    override fun initObservers() {
        binding?.apply {
            mainActivityViewModel.loginNameResult.observe(this@MainActivity) { userName ->
                tvUserName.text = "Hallo $userName"
            }
            mainActivityViewModel.loginStateResult.observe(this@MainActivity) { loginState ->
                tvIsLogin.text = "isLogin = $loginState"
            }
        }
    }
}