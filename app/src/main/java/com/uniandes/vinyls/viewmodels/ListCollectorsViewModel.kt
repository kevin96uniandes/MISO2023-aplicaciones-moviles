package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.repositories.CollectorRepository
import com.uniandes.vinyls.utils.EstadoServicios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListCollectorsViewModel(application: Application) : AndroidViewModel(application) {

    private var connectionState: EstadoServicios = EstadoServicios()
    private val collectorRepository = CollectorRepository(application)
    private var initialCollectors: List<Collector> = emptyList()
    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>> = _collectors
    val appl = application

    fun findAll(){
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO) {

                try {
                    val hasConnectivity = connectionState.validarConexionIntenet(appl.applicationContext)
                    val collectorResponse = collectorRepository.findAll(hasConnectivity)
                    initialCollectors = collectorResponse

                    collectorRepository.createCollectorDB(collectorResponse)
                    _collectors.postValue(collectorResponse)
                } catch (ex: Exception){
                    Log.e("Error", "Ha ocurrido una excepción: ${ex.message} ${ex.localizedMessage} ${ex.printStackTrace()}")
                }
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListCollectorsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ListCollectorsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}