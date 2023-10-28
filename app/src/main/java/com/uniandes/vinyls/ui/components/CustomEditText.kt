package com.uniandes.vinyls.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.uniandes.vinyls.R

class CustomEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

        setBackgroundResource(R.drawable.rounded_background)
        setPadding(16, 0, 16, 0)
        textSize = 16f // Tamaño del texto
        setTextColor(ContextCompat.getColor(context, R.color.vinyls_yellow))
        setHintTextColor(ContextCompat.getColor(context, R.color.black))
    }

}