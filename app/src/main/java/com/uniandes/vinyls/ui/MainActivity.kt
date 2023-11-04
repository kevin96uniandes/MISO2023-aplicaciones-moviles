package com.uniandes.vinyls.ui

import android.os.Bundle
import android.widget.Button
import com.uniandes.vinyls.R
import com.uniandes.vinyls.databinding.ActivityMainBinding

class MainActivity : CrossIntentActivity() {
    private lateinit var btnSignAsCollector: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        btnSignAsCollector = findViewById(R.id.btn_sign_in_as_collectors)

        btnEvents()
    }

    private fun btnEvents() {
        btnSignAsCollector.setOnClickListener {
            nextActivity(DashboardActivity::class.java)
        }

    }
}