package com.hikizan.hikigram.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.hikizan.hikigram.presentation.reuseable.CustomHikizanLoading

abstract class HikizanActivityBase<VB: ViewBinding> : AppCompatActivity() {
    protected var binding: VB? = null

    private val loading: CustomHikizanLoading by lazy {
        CustomHikizanLoading(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = initViewBinding()
        setContentView(binding?.root)

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    protected fun showLoading() {
        loading.show()
    }

    protected fun hideLoading() {
        if (loading.isShowing) {
            loading.cancel()
        }
    }

    abstract fun initViewBinding(): VB
    abstract fun initIntent()
    abstract fun initUI()
    abstract fun initAction()
    abstract fun initProcess()
    abstract fun initObservers()
}