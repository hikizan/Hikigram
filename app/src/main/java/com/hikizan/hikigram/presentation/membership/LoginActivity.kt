package com.hikizan.hikigram.presentation.membership

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityLoginBinding
import com.hikizan.hikigram.presentation.MainActivity
import com.hikizan.hikigram.utils.emailFieldValidation
import com.hikizan.hikigram.utils.ext.isValidPassword
import com.hikizan.hikigram.utils.hikizanEmailStream
import com.hikizan.hikigram.utils.hikizanPasswordStream
import com.hikizan.hikigram.utils.passwordFieldVlaidation
import com.hikizan.hikigram.utils.showEmailExistAlert
import com.hikizan.hikigram.utils.showPasswordExistAlert
import io.reactivex.Observable

class LoginActivity : HikizanActivityBase<ActivityLoginBinding>() {

    companion object {

        fun startNewTask(context: Context) {
            context.startActivity(
                Intent(context, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            )
        }
    }

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
            lavLogin.playAnimation()
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

    override fun initProcess() {
    }

    @SuppressLint("CheckResult")
    override fun initObservers() {
        binding?.apply {
            emailFieldValidation(etEmail, tilEmail)
            passwordFieldVlaidation(etPassword, tilPassword)

            val emailStream = hikizanEmailStream(etEmail)
            emailStream.subscribe {
                showEmailExistAlert(this@LoginActivity, tilEmail, it)
            }

            val passwordStream = hikizanPasswordStream( etPassword)
            passwordStream.subscribe {
                showPasswordExistAlert(this@LoginActivity, tilPassword, it)
            }

            val invalidFieldStream = Observable.combineLatest(
                emailStream,
                passwordStream
            ) { emailInvalid: Boolean, passwordInvalid: Boolean ->
                !emailInvalid && !passwordInvalid
            }
            invalidFieldStream.subscribe { isValid ->
                if (isValid) {
                    btnSignIn.isEnabled = true
                    btnSignIn.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, android.R.color.holo_green_dark))
                } else {
                    btnSignIn.isEnabled = false
                    btnSignIn.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, android.R.color.darker_gray))
                }
            }
        }
    }
}