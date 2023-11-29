package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.uniandes.vinyls.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.events.SingleLiveEvent
import com.uniandes.vinyls.repositories.TracksRepository
import com.uniandes.vinyls.utils.EstadoServicios
import kotlinx.coroutines.launch

class AsociateTrackViewModel(application: Application) : AndroidViewModel(application) {
    private val trackRepository = TracksRepository(application)
    val eventSuccessful = SingleLiveEvent<Boolean>()
    private val estadoServicios = EstadoServicios()
    val aplicacion = application

    fun asociateTrack(trackData: Map<String, String>, id: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO){
                try {
                    val estadoInternet = estadoServicios.validarConexionIntenet(aplicacion.applicationContext)
                    trackRepository.asociarTrack(trackData, id, estadoInternet)
                    eventSuccessful.postValue(true)
                } catch(e: Exception) {
                    Log.e("creating_album", e.stackTraceToString())
                    eventSuccessful.postValue(false)
                }
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AsociateTrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AsociateTrackViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}