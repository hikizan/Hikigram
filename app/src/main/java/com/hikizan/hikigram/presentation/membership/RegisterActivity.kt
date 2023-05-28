package com.hikizan.hikigram.presentation.membership

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityRegisterBinding
import com.hikizan.hikigram.utils.ext.setupHikizanToolbar

class RegisterActivity : HikizanActivityBase<ActivityRegisterBinding>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, RegisterActivity::class.java)
            )
        }
    }

    override fun initViewBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
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
            setupHikizanToolbar(
                toolbarLayout = toolbarLayoutRegister,
                title = resources.getString(R.string.title_register_page),
                isChild = true
            )
        }
    }

    override fun initAction() {
        binding?.apply {
            btnRegister.setOnClickListener {
                finish()
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}