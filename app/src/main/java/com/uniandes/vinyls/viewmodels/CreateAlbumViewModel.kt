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
import kotlinx.coroutines.launch

class CreateAlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val albumRepository = AlbumRepository(application)
    val eventSuccessful = SingleLiveEvent<Boolean>()

    fun createAlbum(albumData: Map<String, String>) {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO){
                try {
                    albumRepository.createAlbum(albumData)
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
            if (modelClass.isAssignableFrom(CreateAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreateAlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}