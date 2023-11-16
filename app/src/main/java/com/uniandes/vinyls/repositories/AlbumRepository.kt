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
import com.uniandes.vinyls.network.Mocks.MockAlbumServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository (private val application: Application) {

    suspend fun createAlbum(album: Map<String, Any>, isInternet: Boolean) {

        Log.d("createAlbum", "createAlbum: ejecutando")
        if (isInternet){
           try {
               AlbumServiceAdapter.getInstance(application).createAlbum(album)
               guardarAlbumBD(album)
           }catch(ex: Exception){
               Log.e("createAlbum", "error a la hora de crear el album ${ex.printStackTrace()}" )
               guardarAlbumBD(album)
           }
        }else{
            Log.d("createAlbum", "createAlbum: ejecutando sin internet")
            guardarAlbumBD(album)
        }
    }

    suspend fun getAlbums(isInternet: Boolean): List<Album> {
        var albums: List<Album>
        val db = VinylDB.getDatabase(application.applicationContext)
        val isRunningTest = validarTest()
        if (!isRunningTest){
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
        }else{
            albums = MockAlbumServiceAdapter.getInstance(application).getAlbumsMocks()
        }

        Log.d("lista albums", albums.toString())
        return albums
    }

    suspend fun obtenerAlbumPorId(id: Int): Album{

        var album: Album?
        val isRunningTest = validarTest()
        if (!isRunningTest){
            val db = VinylDB.getDatabase(application.applicationContext)
            album = db.albumDao().getAlbumById(id)
            if(album != null){
                return album
            }else{
                album = AlbumServiceAdapter.getInstance(application).getAlbumById(id)
                return album

            }
        }else {
            album = MockAlbumServiceAdapter.getInstance(application).getAlbumById(1)
            return album
        }
    }

    suspend fun guardarAlbumsBD(albums: List<Album>){
        val db = VinylDB.getDatabase(application.applicationContext)

        Log.d("guardado db", "ejecutando ")
            for(album in albums){
                db.albumDao().insert(album)
            }
            Log.d("Count db", "${db.albumDao().countAlbums()}")
            Log.d("guardado db", "guardado ")
    }

    suspend fun guardarAlbumBD(albumMap: Map<String, Any>){
        val db = VinylDB.getDatabase(application.applicationContext)

        val createdAlbum = Album.fromMap(albumMap)
        db.albumDao().insert(createdAlbum)

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