package com.hikizan.hikigram.presentation.membership

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityRegisterBinding
import com.hikizan.hikigram.presentation.reuseable.SuccessPageActivity
import com.hikizan.hikigram.utils.emailFieldValidation
import com.hikizan.hikigram.utils.ext.isValidPassword
import com.hikizan.hikigram.utils.ext.setupHikizanToolbar
import com.hikizan.hikigram.utils.passwordFieldVlaidation

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
                registerFormValidation()
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        binding?.apply {
            emailFieldValidation(etRegisterEmail, tilRegisterEmail)
            passwordFieldVlaidation(etRegisterPassword, tilRegisterPassword)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun registerFormValidation() = binding?.apply {
        if (etRegisterPassword.text.toString() == etConfirmPassword.text.toString()) {
            tilRegisterEmail.helperText = emailValidate(
                etRegisterEmail.text.toString()
            )
            tilRegisterPassword.helperText = passwordValidate(
                etRegisterPassword.text.toString()
            )

            val isErrorEmailForm = tilRegisterEmail.helperText != null
            val isErrorPasswordForm = tilRegisterPassword.helperText != null

            if (isErrorEmailForm || isErrorPasswordForm) {
                Toast.makeText(
                    this@RegisterActivity,
                    resources.getString(R.string.message_form_incorrect),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //post for register account (coming soon!)
                directToSuccessPage()
            }

        } else {
            Toast.makeText(
                this@RegisterActivity,
                resources.getString(R.string.message_password_not_match),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun emailValidate(email: String): String? {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return resources.getString(R.string.message_form_incorrect)
        }
        return null
    }

    private fun passwordValidate(password: String): String? {
        if (!password.isValidPassword()) {
            return resources.getString(R.string.message_form_incorrect)
        }
        return null
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