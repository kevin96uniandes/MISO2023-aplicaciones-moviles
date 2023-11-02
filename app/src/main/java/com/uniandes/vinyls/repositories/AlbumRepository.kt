package com.uniandes.vinyls.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.uniandes.vinyls.database.AlbumDAO
import com.uniandes.vinyls.database.VinylDB
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.network.AlbumServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository (private val application: Application) {

    suspend fun createAlbum(album: Map<String, Any>) {
        AlbumServiceAdapter.getInstance(application).createAlbum(album)
        val db = VinylDB.getDatabase(application.applicationContext)
        db.albumDao().deleteAll()
    }

    suspend fun getAlbums(isInternet: Boolean): List<Album> {
        var albums: List<Album>
        val db = VinylDB.getDatabase(application.applicationContext)

        if (isInternet){
            try {
                albums = AlbumServiceAdapter.getInstance(application).getAlbums()
            }catch(ex: Exception){
                Log.e("Error", "Ha ocurrido una excepción: ${ex.message} ${ex.localizedMessage} ${ex.printStackTrace()}")
                albums = db.albumDao().getAlbums()
            }

        }else{
            albums = db.albumDao().getAlbums()
        }
        Log.d("lista albums", albums.toString())
        return albums
    }

    suspend fun guardarAlbumBD(albums: List<Album>){
        val db = VinylDB.getDatabase(application.applicationContext)

        Log.d("guardado db", "ejecutando ")
            for(album in albums){
                db.albumDao().insert(album)
            }
            Log.d("Count db", "${db.albumDao().countAlbums()}")
            Log.d("guardado db", "guardado ")
    }
}