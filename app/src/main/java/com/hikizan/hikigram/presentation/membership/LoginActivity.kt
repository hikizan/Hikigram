package com.hikizan.hikigram.presentation.membership

import android.os.Bundle
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityLoginBinding
import com.hikizan.hikigram.presentation.MainActivity

class LoginActivity : HikizanActivityBase<ActivityLoginBinding>() {

    override fun initViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
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

        }
    }

    override fun initAction() {
        binding?.apply {
            btnSignUp.setOnClickListener {
                RegisterActivity.start(this@LoginActivity)
            }
            btnSignIn.setOnClickListener {
                MainActivity.start(this@LoginActivity)
            }
        }
    }

    override fun initProcess(){
    }

    override fun initObservers() {
    }
}