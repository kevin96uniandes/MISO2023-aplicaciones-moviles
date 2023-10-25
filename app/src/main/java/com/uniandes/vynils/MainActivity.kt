package com.uniandes.vynils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uniandes.vynils.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}