package com.uniandes.vinyls.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.uniandes.vinyls.R

class CustomButton : androidx.appcompat.widget.AppCompatButton {

    private val paint = Paint()
    private val rect = RectF()
    private val path = Path()
    private var customText: String = ButtonsActions.SAVE.action

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
        // Configura el estilo de tu botón personalizado
        paint.color = Color.BLUE
        paint.textSize = 24f
        setTextColor(Color.WHITE)
    }

    fun setCustomText(action: ButtonsActions) {
        customText = action.action
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, 20f, 20f, Path.Direction.CW)
        paint.color = ContextCompat.getColor(context, R.color.vinyls_yellow)

        canvas.drawPath(path, paint)
        // Calcula la posición para centrar el texto
        val bounds = Rect()
        paint.getTextBounds(customText, 0, customText.length, bounds)
        val x = (width - bounds.width()) / 2f
        val y = height / 2f - (bounds.top + bounds.bottom) / 2f
        paint.color = ContextCompat.getColor(context, R.color.black)
        paint.textSize = 40f
        paint.isFakeBoldText = true
        // Dibuja el texto centrado en el botón
        canvas.drawText(customText, x, y, paint)
    }
}

enum class ButtonsActions(val action: String) {
    SAVE("Guardar"),
    UPDATE("Actualizar");
}