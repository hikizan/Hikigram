package com.hikizan.hikigram.utils.ext

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.hikizan.hikigram.databinding.HikizanToolbarLayoutBinding

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

fun String?.orEmptyString(): String {
    return this ?: ""
}

fun String.isValidPassword(): Boolean {
    if (this.length < 8) return false
    if (this.filter { it.isDigit() }.firstOrNull() == null) return false
    if (this.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
    if (this.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
    if (this.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

    return true
}