package com.uniandes.vinyls.network.Mocks

import android.content.Context
import com.uniandes.vinyls.models.Performer
import com.uniandes.vinyls.network.NetworkServiceAdapter

class MockPerformerServiceAdapter (context: Context) : NetworkServiceAdapter(context) {
    companion object {
        @Volatile
        private var instance: MockPerformerServiceAdapter? = null

        fun getInstance(context: Context): MockPerformerServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: MockPerformerServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    fun findAll() : List<Performer> {
        return listOf()
    }
}