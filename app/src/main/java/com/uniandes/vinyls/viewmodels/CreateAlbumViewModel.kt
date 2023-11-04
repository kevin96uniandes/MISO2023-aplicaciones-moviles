package com.uniandes.vinyls.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val nameField = MutableLiveData("")
    val nameError = MutableLiveData<String?>(null)
    val eventSuccessful = SingleLiveEvent<Boolean>()

    fun createAlbum(albumData: Map<String, String>) {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO){
                try {
                    albumRepository.createAlbum(albumData)
                    eventSuccessful.postValue(true)
                } catch(e: Exception) {
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

    fun validateName() {
        val name = nameField.value.orEmpty()
        if (name.isEmpty()) {
            nameError.value = "El campo de nombre no puede estar vacío"
        } else {
            nameError.value = null
        }
    }
}