package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.models.Performer
import com.uniandes.vinyls.repositories.PerformerRepository
import com.uniandes.vinyls.utils.EstadoServicios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListPerformersViewModel (application: Application) : AndroidViewModel(application){
    private var connectionState: EstadoServicios = EstadoServicios()
    private val performerRepository = PerformerRepository(application)
    private var initialPerformers: List<Performer> = emptyList()
    private val _performers = MutableLiveData<List<Performer>>()
    val performers: LiveData<List<Performer>> = _performers
    val appl = application

    fun findAll(){
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO) {

                try {
                    val hasConnectivity = connectionState.validarConexionIntenet(appl.applicationContext)
                    val performerResponse = performerRepository.findAll(hasConnectivity)
                    initialPerformers = performerResponse

                    performerRepository.createPerformerDB(performerResponse)
                    _performers.postValue(performerResponse)
                } catch (ex: Exception){
                    Log.e("Error", "Ha ocurrido una excepción: ${ex.message} ${ex.localizedMessage} ${ex.printStackTrace()}")
                }
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListPerformersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ListPerformersViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}