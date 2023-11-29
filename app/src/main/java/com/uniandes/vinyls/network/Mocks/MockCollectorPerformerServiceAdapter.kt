package com.uniandes.vinyls.network.Mocks

import android.content.Context
import com.uniandes.vinyls.models.CollectorPerformer
import com.uniandes.vinyls.network.NetworkServiceAdapter

class MockCollectorPerformerServiceAdapter (context: Context) : NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: MockCollectorPerformerServiceAdapter? = null

        fun getInstance(context: Context): MockCollectorPerformerServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: MockCollectorPerformerServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    fun findAll() : List<CollectorPerformer> {
        return listOf()
    }

}