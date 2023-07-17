package com.hikizan.hikigram.utils.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hikizan.hikigram.R
import com.hikizan.hikigram.databinding.HikizanToolbarLayoutBinding
import com.hikizan.hikigram.utils.constants.AppConstants
import com.kennyc.view.MultiStateView
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun AppCompatActivity.setupHikizanToolbar(
    toolbarLayout: HikizanToolbarLayoutBinding,
    title: String,
    isChild: Boolean = false,
) {
    with(toolbarLayout) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title.orEmptyString()
        supportActionBar?.setDisplayHomeAsUpEnabled(isChild)
        toolbar.setTitleTextColor(Color.WHITE)
    }
}

fun Context.showToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun MultiStateView.showDefaultState() {
    viewState = MultiStateView.ViewState.CONTENT
}

fun MultiStateView.showLoadingState() {
    viewState = MultiStateView.ViewState.LOADING
}

fun MultiStateView.showEmptyState() {
    viewState = MultiStateView.ViewState.EMPTY
}

fun MultiStateView.showErrorState(
    message: String? = null,
    action: Pair<String, (() -> Unit)>? = null
) {
    viewState = MultiStateView.ViewState.ERROR
    val view = getView(MultiStateView.ViewState.ERROR)

    message?.let {
        val tvError = view?.findViewById<TextView>(R.id.tvError)
        if (message.isNotEmpty()) {
            tvError?.text = message
        }
    }

    action?.let { pair ->
        val btnError = view?.findViewById<Button>(R.id.btnRetry)
        btnError?.text = pair.first

        btnError?.setOnClickListener { pair.second.invoke() }
    }
}

val emptyString: String = ""

fun String?.orEmptyString(): String {
    return this ?: ""
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun String.isValidPassword(): Boolean {
    if (this.length < 8) return false
    if (this.filter { it.isDigit() }.firstOrNull() == null) return false
    if (this.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
    if (this.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
    if (this.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

    return true
}

fun String.setBearer(): String {
    return "Bearer $this"
}

fun String.hikigramDateFormat(): String {
    return if (VERSION.SDK_INT >= VERSION_CODES.O) {
        LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME).format(
            DateTimeFormatter.ofPattern(
                AppConstants.DATE_FORMAT,
                Locale("id", "ID")
            )
        )
    } else {
        SimpleDateFormat(
            /* date formatter */
            AppConstants.DATE_FORMAT, Locale("id", "ID")
        ).format(
            /* date source */
            SimpleDateFormat(AppConstants.DATE_PARSER_SOURCE).parse(
                this
            ) as Date
        )
    }
}

/* Note: if you'll use this, you must apply on Dispatchers.IO scope! */
fun String.imageUrlToBitmap(context: Context): Bitmap {
    return Glide.with(context)
        .asBitmap()
        .load(this)
        .override(150, 150)
        .circleCrop()
        .submit()
        .get()
}