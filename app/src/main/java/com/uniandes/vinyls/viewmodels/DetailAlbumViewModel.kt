package com.uniandes.vinyls.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailAlbumViewModel (application: Application, albumId: Int) : AndroidViewModel(application) {

    private val albumRepository = AlbumRepository(application)
    private val id = albumId
    val _album = MutableLiveData<Album>()
    init {
        getAlbumById(id)
    }

    fun getAlbumById(id: Int){
        viewModelScope.launch (Dispatchers.Default) {
            withContext(Dispatchers.IO){
                Log.d("DetailAlbumViewModel", "id: ${id} ")
                val albumData = albumRepository.obtenerAlbumPorId(id)
                _album.postValue(albumData)
            }
        }
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailAlbumViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}