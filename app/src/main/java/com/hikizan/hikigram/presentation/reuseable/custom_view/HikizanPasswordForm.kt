package com.hikizan.hikigram.presentation.reuseable.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hikizan.hikigram.R
import com.hikizan.hikigram.utils.ext.isValidPassword
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

class HikizanPasswordForm : TextInputLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    lateinit var hikizanEditText: TextInputEditText

    private fun init(context: Context) {
        boxBackgroundMode = BOX_BACKGROUND_NONE
        isHintEnabled = false
        hikizanEditText = TextInputEditText(context)
        createEditBox(hikizanEditText, this)
    }

    private fun createEditBox(editText: TextInputEditText, textInputLayout: TextInputLayout) {
        val scale = resources.displayMetrics.density
        val sixteenDpAsPixels = (16/*size in DP*/ * scale + 0.5f).toInt()
        editText.setPadding(
            sixteenDpAsPixels,
            sixteenDpAsPixels,
            sixteenDpAsPixels,
            sixteenDpAsPixels
        )
        editText.hint = resources.getString(R.string.label_password)
        editText.background = ContextCompat.getDrawable(context, R.drawable.bg_hikizan_outline_form)
        editText.transformationMethod = PasswordTransformationMethod.getInstance()
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (!charSequence.toString().isValidPassword()) {
                    /* is not valid */
                    textInputLayout.error = context.getString(R.string.message_password_not_valid)
                } else {
                    /* is valid */
                    textInputLayout.error = null
                }
            }

            override fun afterTextChanged(S: Editable?) {
                // Do nothing
            }
        })
        addView(editText)
    }

    fun isInvalidForm(): Observable<Boolean> {
        return RxTextView.textChanges(hikizanEditText)
            .map { value ->
                !value.toString().isValidPassword()
            }
    }
}