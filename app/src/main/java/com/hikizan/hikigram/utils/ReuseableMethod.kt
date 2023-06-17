package com.hikizan.hikigram.utils

import android.content.Context
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hikizan.hikigram.R
import com.hikizan.hikigram.utils.ext.isValidPassword
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

fun hikizanMandatoryFormStream(etMandatoryForm: TextInputEditText): Observable<Boolean> {
    return RxTextView.textChanges(etMandatoryForm)
        .map { mandatoryForm ->
            mandatoryForm.isEmpty()
        }
}

fun showMandatoryFormExistAlert(
    context: Context,
    tilMandatoryForm: TextInputLayout,
    isNotValid: Boolean
) {
    tilMandatoryForm.error =
        if (isNotValid) {
            context.getString(R.string.message_form_empty)
        } else null
}

fun hikizanEmailStream(etEmail: TextInputEditText): Observable<Boolean> {
    return RxTextView.textChanges(etEmail)
        .skipInitialValue()
        .map { email ->
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
}

fun showEmailExistAlert(
    context: Context,
    tilEmail: TextInputLayout,
    isNotValid: Boolean
) {
    tilEmail.error =
        if (isNotValid) {
            context.getString(R.string.message_email_not_valid)
        } else null
}

fun hikizanPasswordStream(etPassword: TextInputEditText): Observable<Boolean> {
    return RxTextView.textChanges(etPassword)
        .skipInitialValue()
        .map {
            !it.toString().isValidPassword()
        }
}

fun showPasswordExistAlert(
    context: Context,
    tilPassword: TextInputLayout,
    isNotValid: Boolean
) {
    tilPassword.error =
        if (isNotValid) {
            context.getString(R.string.message_password_not_valid)
        } else null
}

fun hikizanPasswordConfirmationStream(
    etPassword: TextInputEditText,
    etConfirmPassword: TextInputEditText
): Observable<Boolean> = Observable.merge(
    RxTextView.textChanges(etPassword)
        .map { password ->
            password.toString() != etConfirmPassword.text.toString()
        },
    RxTextView.textChanges(etConfirmPassword)
        .map { confirmPassword ->
            confirmPassword.toString() != etPassword.text.toString()
        }
)

fun showConfirmPasswordExistAlert(
    context: Context,
    tilConfirmPassword: TextInputLayout,
    isNotValid: Boolean
) {
    tilConfirmPassword.error =
        if (isNotValid) {
            context.getString(R.string.message_password_not_match)
        } else null
}