package com.uniandes.vinyls.ui.components

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.uniandes.vinyls.EditTextType
import com.uniandes.vinyls.R

class CustomEditText : AppCompatEditText {

    var type: String? = null
    var text: String
    get() {
        return super.getText().toString()
    }
    set(value) {
        super.setText(value)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()

        // Obtén el valor del atributo "type" del conjunto de atributos
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        type = typedArray.getString(R.styleable.CustomEditText_type)
        typedArray.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()

        // Obtén el valor del atributo "type" del conjunto de atributos
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        type = typedArray.getString(R.styleable.CustomEditText_type)
        typedArray.recycle()
    }

    private fun init() {

        setBackgroundResource(R.drawable.rounded_background)
        setPadding(16, 0, 16, 0)
        textSize = 16f // Tamaño del texto
        setTextColor(ContextCompat.getColor(context, R.color.vinyls_yellow))
        setHintTextColor(ContextCompat.getColor(context, R.color.vinyls_orange_lite))

        if (type == EditTextType.URL.name) {
            inputType = InputType.TYPE_TEXT_VARIATION_URI
        }
    }

}