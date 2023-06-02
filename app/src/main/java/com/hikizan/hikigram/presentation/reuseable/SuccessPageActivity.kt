package com.hikizan.hikigram.presentation.reuseable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivitySuccessPageBinding
import com.hikizan.hikigram.utils.constants.BundleKeys
import com.hikizan.hikigram.utils.ext.orEmptyString

class SuccessPageActivity : HikizanActivityBase<ActivitySuccessPageBinding>() {

    companion object {
        const val RESULT_CODE_POSITIVE_BUTTON_CLICKED = 1100

        fun startActivityResult(
            context: Context,
            title: String,
            desc: String,
            animationViewResourceId: Int,
            positiveButtonText: String
        ): Intent {
            return Intent(context, SuccessPageActivity::class.java).apply {
                putExtra(BundleKeys.KEY_SUCCESS_PAGE_TITLE, title)
                putExtra(BundleKeys.KEY_SUCCESS_PAGE_DESC, desc)
                putExtra(BundleKeys.KEY_SUCCESS_PAGE_ANIMATION, animationViewResourceId)
                putExtra(BundleKeys.KEY_SUCCESS_PAGE_POSITIVE_BUTTON, positiveButtonText)
            }
        }
    }

    override fun initViewBinding(): ActivitySuccessPageBinding {
        return ActivitySuccessPageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initIntent()
        initUI()
        initAction()
        initProcess()
        initObservers()
    }

    private lateinit var title: String
    private var description: CharSequence? = null
    private lateinit var positiveButtonText: String
    private var animationViewResourceId = 0

    override fun initIntent() {
        title = intent.getStringExtra(BundleKeys.KEY_SUCCESS_PAGE_TITLE).orEmptyString()
        description = intent.getCharSequenceExtra(BundleKeys.KEY_SUCCESS_PAGE_DESC)
        animationViewResourceId = intent.getIntExtra(BundleKeys.KEY_SUCCESS_PAGE_ANIMATION, 0)
        positiveButtonText = intent.getStringExtra(BundleKeys.KEY_SUCCESS_PAGE_POSITIVE_BUTTON).orEmptyString()
    }

    override fun initUI() {
        binding?.apply {
            tvTitleSuceessPage.text = title
            tvDescSuccessPage.text = description
            btnPositive.text = positiveButtonText
            lavSuccessPage.setAnimation(animationViewResourceId)
        }
    }

    override fun initAction() {
        binding?.apply {
            btnPositive.setOnClickListener {
                setResult(RESULT_CODE_POSITIVE_BUTTON_CLICKED)
                Log.d("SuccessPage", "initAction: ")
                finish()
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }
}