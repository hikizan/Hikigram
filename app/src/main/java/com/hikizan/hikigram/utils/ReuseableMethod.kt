package com.hikizan.hikigram.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hikizan.hikigram.R
import com.hikizan.hikigram.utils.constants.AppConstants.FILENAME_FORMAT
import com.hikizan.hikigram.utils.ext.isValidPassword
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

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

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}