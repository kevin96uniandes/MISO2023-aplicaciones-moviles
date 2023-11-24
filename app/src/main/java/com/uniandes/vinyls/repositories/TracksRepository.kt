package com.uniandes.vinyls.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.uniandes.vinyls.database.AlbumDAO
import com.uniandes.vinyls.database.VinylDB
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.models.Track
import com.uniandes.vinyls.network.AlbumServiceAdapter
import com.uniandes.vinyls.network.Mocks.MockAlbumServiceAdapter
import com.uniandes.vinyls.network.TrackServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TracksRepository (private val application: Application) {

    suspend fun asociarTrack(track: Map<String, Any>, id: Int, isInternet: Boolean) {
        Log.d("asociarTrack", "asociar track: ejecutando")
        if (isInternet){
           try {
               TrackServiceAdapter.getInstance(application).asociateTrack(track, id)
               delleteAllTrackAndAlbums()
           }catch(ex: Exception){
               Log.e("asociarTrack", "error a la hora de asociar el track ${ex.printStackTrace()}" )
               guardarTrackAlbumBD(id, track)
           }
        }else{
            Log.d("asociarTrack", "asociarTrack: ejecutando sin internet")
            guardarTrackAlbumBD(id, track)
        }
    }

    suspend fun delleteAllTrackAndAlbums(){
        val db = VinylDB.getDatabase(application.applicationContext)
        db.trackDao().deleteAll()
        db.albumDao().deleteAll()
    }

    suspend fun guardarTrackAlbumBD(id: Int, trackMap: Map<String, Any>){
        val db = VinylDB.getDatabase(application.applicationContext)

        val createdTrack = Track.fromMap(trackMap)
        val album = db.albumDao().getAlbumById(id)
        album!!.addTrack(createdTrack)

        db.albumDao().update(album)

        Log.d("guardado db individual", "guardado ")
    }


    suspend fun validarTest(): Boolean {

        val isRunningTest : Boolean by lazy {
            try {
                Class.forName("androidx.test.espresso.Espresso")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
        }

        return isRunningTest
    }
}