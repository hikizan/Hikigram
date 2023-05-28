package com.hikizan.hikigram.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityMainBinding

class MainActivity : HikizanActivityBase<ActivityMainBinding>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, MainActivity::class.java)
            )
        }
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
    }

    override fun initAction() {
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }
}