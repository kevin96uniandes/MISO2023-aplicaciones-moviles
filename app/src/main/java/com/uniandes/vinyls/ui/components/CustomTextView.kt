package com.uniandes.vinyls.ui.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.uniandes.vinyls.R

class CustomTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setPadding(16, 0, 16, 14)
        textSize = 18f // Tamaño de texto personalizado
        setTextColor(ContextCompat.getColor(context, R.color.white)) // Color de texto personalizado
        setTypeface(null, Typeface.BOLD)
    }

}