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