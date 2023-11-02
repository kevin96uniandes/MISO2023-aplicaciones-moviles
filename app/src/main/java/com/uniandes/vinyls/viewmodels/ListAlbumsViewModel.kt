package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.repositories.AlbumRepository
import com.uniandes.vinyls.utils.EstadoServicios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListAlbumsViewModel(application: Application) : AndroidViewModel(application)  {

    private val albumsRepository = AlbumRepository(application)
    private val estadoServicios = EstadoServicios()
    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums
    val aplicacion = application
    fun getListAlbums(){
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO) {

                try {
                    val estadoInternet = estadoServicios.validarConexionIntenet(aplicacion.applicationContext)
                    val albumsResponse = albumsRepository.getAlbums(estadoInternet)
                    _albums.postValue(albumsResponse)

                    albumsRepository.guardarAlbumBD(albumsResponse)
                } catch (ex: Exception){
                    Log.e("Error", "Ha ocurrido una excepción: ${ex.message} ${ex.localizedMessage} ${ex.printStackTrace()}")
                }

            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListAlbumsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ListAlbumsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}