package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.models.CollectorAlbum
import com.uniandes.vinyls.repositories.CollectorAlbumRepository
import com.uniandes.vinyls.utils.EstadoServicios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorAlbumViewModel (application: Application) : AndroidViewModel(application) {

    private var connectionState: EstadoServicios = EstadoServicios()
    private val collectorAlbumRepository = CollectorAlbumRepository (application)
    private var initialCollectorAlbum: List<CollectorAlbum> = listOf()
    private val _collectorAlbum = MutableLiveData<List<CollectorAlbum>>()
    val collectorAlbum: LiveData<List<CollectorAlbum>> = _collectorAlbum
    val appl = application

    fun findAll(collectorId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO) {
                try {
                    val hasConnectivity = connectionState.validarConexionIntenet(appl.applicationContext)
                    val collectorResponse = collectorAlbumRepository.findAll(collectorId, hasConnectivity)
                    initialCollectorAlbum = collectorResponse

                    collectorAlbumRepository.createCollectorAlbumDB(collectorResponse)
                    _collectorAlbum.postValue(collectorResponse)
                } catch (ex: Exception){
                    Log.e("Error", "Ha ocurrido una excepción: ${ex.message} ${ex.localizedMessage} ${ex.printStackTrace()}")
                }
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorAlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}