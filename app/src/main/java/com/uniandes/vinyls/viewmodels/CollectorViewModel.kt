package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.NoInternetException
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.repositories.CollectorRepository
import com.uniandes.vinyls.utils.EstadoServicios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorViewModel(application: Application) : AndroidViewModel(application) {

    private var connectionState: EstadoServicios = EstadoServicios()
    private val collectorRepository = CollectorRepository(application)
    private var initialCollectors: List<Collector> = emptyList()
    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>> = _collectors
    private var _collector: MutableLiveData<Collector?> = MutableLiveData(null)
    val collector: LiveData<Collector?> = _collector
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

    fun findById(collectorId: Int){
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO) {
                val collectorData = collectorRepository.findById(collectorId)
                _collector.postValue(collectorData)
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}