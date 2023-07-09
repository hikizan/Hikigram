package com.hikizan.hikigram.presentation.reuseable.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hikizan.hikigram.R

class HikizanTextInputLayout : TextInputLayout {

    private lateinit var editText: TextInputEditText
    private var tilErrorMessage: String? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int? = null) {
        //removeAllViews();
        //setWillNotDraw(false)


        boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_NONE
        editText = TextInputEditText(context)
        createEditBox(editText, this)
        error = tilErrorMessage
    }

    private fun createEditBox(editText: TextInputEditText, textInputLayout: TextInputLayout) {
        val scale = resources.displayMetrics.density
        val sixteenDpAsPixels = (16/*size in DP*/ * scale + 0.5f).toInt()
        val twentyDpAsPixels = (20/*size in DP*/ * scale + 0.5f).toInt()
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
                if (charSequence.toString().length < 8) {
                    textInputLayout.error = "Password tidak boleh kurang dari 8 karakter"
                } else {
                    textInputLayout.error = null
                }
            }

            override fun afterTextChanged(S: Editable?) {
                // Do nothing
            }
        })
        addView(editText)
    }
}