package com.hikizan.hikigram.presentation.reuseable

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.hikizan.hikigram.R

class CustomHikizanLoading(context: Context) : Dialog(context) {
    init {
        val layoutParams: WindowManager.LayoutParams? =
            window?.attributes
        layoutParams?.gravity = Gravity.CENTER_HORIZONTAL

        window?.attributes = layoutParams
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)

        val view: View = View.inflate(
            context,
            R.layout.hikizan_loading_state_layout,
            null
        )
        setContentView(view)
    }
}