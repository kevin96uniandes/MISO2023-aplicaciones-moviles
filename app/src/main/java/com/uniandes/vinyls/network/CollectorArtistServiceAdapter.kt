package com.uniandes.vinyls.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uniandes.vinyls.models.CollectorArtist
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectorArtistServiceAdapter (context: Context) : NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: CollectorArtistServiceAdapter? = null

        fun getInstance(context: Context): CollectorArtistServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: CollectorArtistServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    suspend fun findAll(collectorId: Int) = suspendCoroutine { cont ->
        requestQueue.add(
            getRequest("/collectors/$collectorId/performers", { response ->
                val gson = Gson()
                val listType = object : TypeToken<List<CollectorArtist>>() {}.type
                val listCollectorArtist: List<CollectorArtist> = gson.fromJson(response, listType)
                cont.resume(listCollectorArtist)
            },{
                cont.resumeWithException(it)
                Log.e("error getting collector artist list", it.toString())
            })
        )
    }
}