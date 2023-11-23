package com.uniandes.vinyls.network.Mocks

import android.content.Context
import com.uniandes.vinyls.models.CollectorAlbum
import com.uniandes.vinyls.network.NetworkServiceAdapter

class MockCollectorAlbumServiceAdapter (context: Context) : NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: MockCollectorAlbumServiceAdapter? = null

        fun getInstance(context: Context): MockCollectorAlbumServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: MockCollectorAlbumServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    fun findAll() : List<CollectorAlbum> {
        return listOf()
    }
}