package com.uniandes.vinyls.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uniandes.vinyls.models.CollectorAlbum
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectorAlbumServiceAdapter(context: Context): NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: CollectorAlbumServiceAdapter? = null

        fun getInstance(context: Context): CollectorAlbumServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: CollectorAlbumServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    suspend fun findAll(collectorId: Int) = suspendCoroutine { cont ->
        requestQueue.add(
            getRequest("/collectors/$collectorId/albums", { response ->
                val gson = Gson()
                val listType = object : TypeToken<List<CollectorAlbum>>() {}.type
                val listCollectorAlbum: List<CollectorAlbum> = gson.fromJson(response, listType)
                cont.resume(listCollectorAlbum)
            },{
              cont.resumeWithException(it)
              Log.e("error getting collector album list", it.toString())
            })
        )
    }
}