package com.hikizan.hikigram.utils

import android.content.Context
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hikizan.hikigram.R
import com.hikizan.hikigram.utils.ext.isValidPassword
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit.MILLISECONDS

/*Define an ObservableTransformer, where we’ll perform the email validation*/
val validateEmailAddress = ObservableTransformer<String, String> { observable ->
    observable.flatMap {
        Observable.just(it).map { it.trim() }
            .filter {// Check whether the user input matches Android’s email pattern
                Patterns.EMAIL_ADDRESS.matcher(it).matches()
            }
/*If the user’s input doesn’t match the email pattern, then throw an erro*/
            .singleOrError()
            .onErrorResumeNext {
                if (it is NoSuchElementException) {
                    Single.error(Exception("Please enter a valid email address"))
                } else {
                    Single.error(it)
                }
            }
            .toObservable()
    }
}


/*If the app encounters an error, then try again*/
fun retryWhenError(
    onError: (ex: Throwable) -> Unit
): ObservableTransformer<String, String> = ObservableTransformer { observable ->
    observable.retryWhen { errors ->
/*Use the flatmap() operator to flatten all emissions into a single Observable*/
        errors.flatMap {
            onError(it)
            Observable.just("")
        }

    }
}

fun emailFieldValidation(
    etEmail: TextInputEditText,
    tilEmail: TextInputLayout
) {
/*Respond to text change events in enterEmail*/
    RxTextView.afterTextChangeEvents(etEmail)
        .skipInitialValue()
        .map {
            tilEmail.error = null
            it.view().text.toString()
        }
/*Ignore all emissions that occur within a 400 milliseconds timespan*/
        .debounce(
            400,
            MILLISECONDS
        ).observeOn(AndroidSchedulers.mainThread()) //Make sure we’re in Android’s main UI thread
/*Apply the validateEmailAddress transformation function*/
        .compose(validateEmailAddress)
        .compose(retryWhenError {
            tilEmail.error = it.message
        })
        .subscribe()
}



val validatePassword = ObservableTransformer<String, String> { observable ->
    observable.flatMap {
        Observable.just(it).map { it.trim() }
            .filter {
                it.isValidPassword()
            }
            .singleOrError()
            .onErrorResumeNext {
                if (it is NoSuchElementException) {
                    Single.error(Exception(
                        "Password must be at least 8 character, 1 lowercase, 1 uppercase, 1 special character, and at least 1 digit number"
                    ))
                } else {
                    Single.error(it)
                }
            }
            .toObservable()
    }
}

fun passwordFieldVlaidation(
    etPassword: TextInputEditText,
    tilPassword: TextInputLayout
) {
    RxTextView.afterTextChangeEvents(etPassword)
        .skipInitialValue()
        .map {
            tilPassword.error = null
            it.view().text.toString()
        }
        .debounce(
            400,
            MILLISECONDS
        ).observeOn(AndroidSchedulers.mainThread())
        .compose(validatePassword)
        .compose(retryWhenError {
            tilPassword.error = it.message
        })
        .subscribe()
}

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