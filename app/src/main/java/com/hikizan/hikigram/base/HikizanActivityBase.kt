package com.hikizan.hikigram.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class HikizanActivityBase<VB: ViewBinding> : AppCompatActivity() {
    protected var binding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = initViewBinding()
        setContentView(binding?.root)

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    abstract fun initViewBinding(): VB
    abstract fun initIntent()
    abstract fun initUI()
    abstract fun initAction()
    abstract fun initProcess()
    abstract fun initObservers()
}