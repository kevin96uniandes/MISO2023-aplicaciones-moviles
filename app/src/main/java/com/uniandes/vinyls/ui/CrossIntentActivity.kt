package com.uniandes.vinyls.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

abstract class CrossIntentActivity : AppCompatActivity() {

    fun nextActivity(activity: Class<*>, flags: Int? = null) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

}