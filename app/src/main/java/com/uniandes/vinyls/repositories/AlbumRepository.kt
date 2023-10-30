package com.uniandes.vinyls.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.uniandes.vinyls.database.AlbumDAO
import com.uniandes.vinyls.database.VinylDB
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.network.AlbumServiceAdapter

class AlbumRepository (private val application: Application) {

    suspend fun createAlbum(album: Map<String, Any>) {
        AlbumServiceAdapter.getInstance(application).createAlbum(album)
        val db = VinylDB.getDatabase(application.applicationContext)
        db.albumDao().deleteAll()
    }
}