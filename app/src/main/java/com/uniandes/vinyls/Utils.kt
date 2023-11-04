package com.uniandes.vinyls

import android.app.Activity
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.uniandes.vinyls.ui.components.CustomEditText

private class Utils

fun <T : View>isFormSuccess(pairs: List<Pair<T,TextView>>, activity: Activity ): Boolean {
    var message = ""
    val listErrorTextView = mutableListOf<TextView>()
    pairs.map {
        message = ""
        val (component, errorTextView) = it
        when(component){
            is CustomEditText -> {
                val editText = component as CustomEditText
                if (editText.text.isEmpty()) {
                    message = activity.getText(R.string.error_required_field_and_not_empty).toString()
                    errorTextView.text =  message
                }else {
                    if (editText.type == EditTextType.URL.name) {
                        if (!Patterns.WEB_URL.matcher(editText.text).matches()) {
                            message = activity.getText(R.string.error_malformed_url).toString()
                            errorTextView.text = message
                        }
                    }
                }
            }
            is EditText -> {
                val editText = component as EditText
                if (editText.text.isEmpty()) {
                    message = activity.getText(R.string.error_required_field_and_not_empty).toString()
                    errorTextView.text =  message
                }
            }
            is Spinner -> {
                val spinner = component as Spinner
                if (spinner.selectedItemPosition == 0){
                    message = activity.getText(R.string.error_spinner_required).toString()
                    errorTextView.text =  message
                }
            }
        }

        if (message.isNotEmpty()) {
            errorTextView.visibility = View.VISIBLE
        } else {
            errorTextView.visibility = View.GONE
        }

        listErrorTextView.add(errorTextView)
    }

    return listErrorTextView.all { it.visibility == View.GONE }
}