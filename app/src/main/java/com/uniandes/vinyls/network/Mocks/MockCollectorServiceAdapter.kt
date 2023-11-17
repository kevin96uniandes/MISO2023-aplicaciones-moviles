package com.uniandes.vinyls.network.Mocks

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.network.NetworkServiceAdapter

class MockCollectorServiceAdapter (context: Context) : NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: MockCollectorServiceAdapter? = null

        fun getInstance(context: Context): MockCollectorServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: MockCollectorServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    fun findAll() : List<Collector> {
        return listOf()
    }
}