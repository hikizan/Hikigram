package com.hikizan.hikigram.presentation.splash_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivitySplashScreenBinding
import com.hikizan.hikigram.presentation.main.MainActivity
import com.hikizan.hikigram.presentation.membership.LoginActivity
import com.hikizan.hikigram.presentation.view_model.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : HikizanActivityBase<ActivitySplashScreenBinding>() {

    companion object {

        const val delayTime: Long = 2500L
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun initViewBinding(): ActivitySplashScreenBinding {
        return ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initIntent()
        initUI()
        initAction()

        Handler(Looper.getMainLooper()).postDelayed({
            initProcess()
            initObservers()
        }, delayTime)
    }

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initProcess() {
        viewModel.getLoginState()
    }

    override fun initObservers() {
        viewModel.loginStateResult.observe(this@SplashScreenActivity) { isLogin ->
            if (isLogin) {
                MainActivity.startNewTask(this@SplashScreenActivity)
            } else {
                LoginActivity.startNewTask(this@SplashScreenActivity)
            }
        }
    }
}