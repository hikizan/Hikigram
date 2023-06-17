package com.hikizan.hikigram.presentation.membership

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityRegisterBinding
import com.hikizan.hikigram.presentation.reuseable.SuccessPageActivity
import com.hikizan.hikigram.utils.ext.setupHikizanToolbar
import com.hikizan.hikigram.utils.hikizanEmailStream
import com.hikizan.hikigram.utils.hikizanMandatoryFormStream
import com.hikizan.hikigram.utils.hikizanPasswordConfirmationStream
import com.hikizan.hikigram.utils.hikizanPasswordStream
import com.hikizan.hikigram.utils.showConfirmPasswordExistAlert
import com.hikizan.hikigram.utils.showEmailExistAlert
import com.hikizan.hikigram.utils.showMandatoryFormExistAlert
import com.hikizan.hikigram.utils.showPasswordExistAlert
import io.reactivex.Observable

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
                //post for register account (coming soon!)
                directToSuccessPage()
            }
        }
    }

    override fun initProcess() {
    }

    @SuppressLint("CheckResult")
    override fun initObservers() {
        binding?.apply {
            val nameRegisterStream = hikizanMandatoryFormStream(etRegisterName)
            nameRegisterStream.subscribe {
                showMandatoryFormExistAlert(
                    this@RegisterActivity, tilRegisterName, it
                )
            }

            val emailRegisterStream = hikizanEmailStream(etRegisterEmail)
            emailRegisterStream.subscribe {
                showEmailExistAlert(
                    this@RegisterActivity, tilRegisterEmail, it
                )
            }

            val passwordRegisterStream = hikizanPasswordStream(etRegisterPassword)
            passwordRegisterStream.subscribe {
                showPasswordExistAlert(
                    this@RegisterActivity, tilRegisterPassword, it
                )
            }

            val passwordConfrimStream = hikizanPasswordConfirmationStream(
                etRegisterPassword, etConfirmPassword
            )
            passwordConfrimStream.subscribe {
                showConfirmPasswordExistAlert(
                    this@RegisterActivity, tilConfirmPassword, it
                )
            }

            val invalidFieldStream = Observable.combineLatest(
                nameRegisterStream,
                emailRegisterStream,
                passwordRegisterStream,
                passwordConfrimStream
            ) { isEmptyName: Boolean, emailInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmationInvalid: Boolean ->
                !isEmptyName && !emailInvalid && !passwordInvalid && !passwordConfirmationInvalid
            }
            invalidFieldStream.subscribe { isValid ->
                if (isValid) {
                    btnRegister.isEnabled = true
                    btnRegister.setBackgroundColor(ContextCompat.getColor(this@RegisterActivity, R.color.purple_700))
                } else {
                    btnRegister.isEnabled = false
                    btnRegister.setBackgroundColor(ContextCompat.getColor(this@RegisterActivity, android.R.color.darker_gray))
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun directToSuccessPage() {
        successPageResult.launch(

            SuccessPageActivity.startActivityResult(
                context = this,
                title = getString(R.string.title_done),
                desc = getString(R.string.message_register_success),
                animationViewResourceId = R.raw.hikizan_anim_done,
                positiveButtonText = getString(R.string.action_go_to_login)
            )
        )
    }

    private val successPageResult =
        registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == SuccessPageActivity.RESULT_CODE_POSITIVE_BUTTON_CLICKED) {
                finish()
                LoginActivity.startNewTask(this)
            }
        }
}