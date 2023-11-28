package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.models.Performer
import com.uniandes.vinyls.repositories.PerformerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPerformerViewModel(application: Application, performerId: Int) : AndroidViewModel(application) {
    private val performerRepository = PerformerRepository(application)
    private val id = performerId
    val _performer = MutableLiveData<Performer>()
    init {
        getPerformerById(id)
    }

    fun getPerformerById(id: Int){
        viewModelScope.launch (Dispatchers.Default) {
            withContext(Dispatchers.IO){
                Log.d("DetailPerformerViewModel", "id: ${id} ")
                val performerData = performerRepository.obtenerPerformerPorId(id)
                _performer.postValue(performerData)
            }
        }
    }

    class Factory(val app: Application, val performerId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailPerformerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailPerformerViewModel(app, performerId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}