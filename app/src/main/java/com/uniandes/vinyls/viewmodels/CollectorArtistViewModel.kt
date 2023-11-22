package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.models.CollectorArtist
import com.uniandes.vinyls.repositories.CollectorArtistRepository
import com.uniandes.vinyls.utils.EstadoServicios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorArtistViewModel (application: Application) : AndroidViewModel(application) {

    private var connectionState: EstadoServicios = EstadoServicios()
    private val collectorArtistRepository = CollectorArtistRepository (application)
    private var initialCollectorArtist: List<CollectorArtist> = listOf()
    private val _collectorArtist = MutableLiveData<List<CollectorArtist>>()
    val collectorArtist: LiveData<List<CollectorArtist>> = _collectorArtist
    val appl = application

    fun findAll(collectorId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO) {

                try {
                    val hasConnectivity = connectionState.validarConexionIntenet(appl.applicationContext)
                    val collectorResponse = collectorArtistRepository.findAll(collectorId, hasConnectivity)
                    initialCollectorArtist = collectorResponse

                    //collectorArtistRepository.createCollectorDB(collectorResponse)
                    _collectorArtist.postValue(collectorResponse)
                } catch (ex: Exception){
                    Log.e("Error", "Ha ocurrido una excepción: ${ex.message} ${ex.localizedMessage} ${ex.printStackTrace()}")
                }
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorArtistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}