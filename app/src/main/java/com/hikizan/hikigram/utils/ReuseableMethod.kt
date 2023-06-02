package com.hikizan.hikigram.utils

import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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