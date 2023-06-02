package com.hikizan.hikigram.presentation.membership

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.hikizan.hikigram.R
import com.hikizan.hikigram.base.HikizanActivityBase
import com.hikizan.hikigram.databinding.ActivityLoginBinding
import com.hikizan.hikigram.presentation.MainActivity
import com.hikizan.hikigram.utils.emailFieldValidation
import com.hikizan.hikigram.utils.ext.isValidPassword
import com.hikizan.hikigram.utils.passwordFieldVlaidation

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
        signInFormValidation()
    }
}
}

override fun initProcess(){
}

override fun initObservers() {
binding?.apply {
    emailFieldValidation(etEmail, tilEmail)
    passwordFieldVlaidation(etPassword, tilPassword)
}
}

private fun signInFormValidation() = binding?.apply {
tilEmail.helperText = emailValidate(
    etEmail.text.toString()
)
tilPassword.helperText = passwordValidate(
    etPassword.text.toString()
)

val isErrorEmailForm = tilEmail.helperText != null
val isErrorPasswordForm = tilPassword.helperText != null

if (isErrorEmailForm || isErrorPasswordForm) {
    Toast.makeText(
        this@LoginActivity,
        resources.getString(R.string.message_form_incorrect),
        Toast.LENGTH_SHORT
    ).show()
} else {
    MainActivity.start(this@LoginActivity)
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
}