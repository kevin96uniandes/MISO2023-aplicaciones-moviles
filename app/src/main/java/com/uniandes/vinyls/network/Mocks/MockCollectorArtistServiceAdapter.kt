package com.uniandes.vinyls.network.Mocks

import android.content.Context
import com.uniandes.vinyls.models.CollectorArtist
import com.uniandes.vinyls.network.NetworkServiceAdapter

class MockCollectorArtistServiceAdapter (context: Context) : NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: MockCollectorArtistServiceAdapter? = null

        fun getInstance(context: Context): MockCollectorArtistServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: MockCollectorArtistServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    fun findAll() : List<CollectorArtist> {
        return listOf()
    }

}