package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.models.CollectorPerformer
import com.uniandes.vinyls.repositories.CollectorPerformerRepository
import com.uniandes.vinyls.utils.EstadoServicios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorPerformerViewModel (application: Application) : AndroidViewModel(application) {

    private var connectionState: EstadoServicios = EstadoServicios()
    private val collectorPerformerRepository = CollectorPerformerRepository (application)
    private var initialCollectorPerformer: List<CollectorPerformer> = listOf()
    private val _collectorPerformer = MutableLiveData<List<CollectorPerformer>>()
    val collectorPerformer: LiveData<List<CollectorPerformer>> = _collectorPerformer
    val appl = application

    fun findAll(collectorId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO) {
                try {
                    val hasConnectivity = connectionState.validarConexionIntenet(appl.applicationContext)
                    val collectorResponse = collectorPerformerRepository.findAll(collectorId, hasConnectivity)
                    initialCollectorPerformer = collectorResponse

                    collectorPerformerRepository.createCollectorPerformerDB(collectorResponse)
                    _collectorPerformer.postValue(collectorResponse)
                } catch (ex: Exception){
                    Log.e("Error", "Ha ocurrido una excepción: ${ex.message} ${ex.localizedMessage} ${ex.printStackTrace()}")
                }
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorPerformerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorPerformerViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}