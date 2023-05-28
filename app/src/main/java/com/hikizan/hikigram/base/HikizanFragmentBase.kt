package com.hikizan.hikigram.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class HikizanFragmentBase<VB: ViewBinding> : Fragment() {
    protected var binding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initViewBinding()
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    abstract fun initViewBinding(): VB
    abstract fun initIntent()
    abstract fun initUI()
    abstract fun initAction()
    abstract fun initProcess()
    abstract fun initObservers()
}