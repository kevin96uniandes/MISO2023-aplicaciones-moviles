package com.uniandes.vinyls.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uniandes.vinyls.databinding.ActivitySplashBinding
import com.uniandes.vinyls.ui.fragments.CreateAlbumFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    companion object {
        private val SPLASH_TIME_OUT = 5000L
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)

        // Utiliza corutinas para realizar una tarea diferida
        GlobalScope.launch(Dispatchers.Main) {

            delay(SPLASH_TIME_OUT) // Retraso de 2 segundos (equivalente a postDelayed en Handler)

            // Coloca el código que deseas ejecutar después del retraso aquí
            // Por ejemplo, puedes iniciar una nueva actividad o realizar otras acciones.
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
