package com.uniandes.vinyls.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uniandes.vinyls.models.CollectorPerformer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectorPerformerServiceAdapter(context: Context) : NetworkServiceAdapter(context){

    companion object {
        @Volatile
        private var instance: CollectorPerformerServiceAdapter? = null

        fun getInstance(context: Context): CollectorPerformerServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: CollectorPerformerServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    suspend fun findAll(collectorId: Int) = suspendCoroutine { cont ->
        requestQueue.add(
            getRequest("/collectors/$collectorId/performers", { response ->
                val gson = Gson()
                val listType = object : TypeToken<List<CollectorPerformer>>() {}.type
                val listCollectorPerformer: List<CollectorPerformer> = gson.fromJson(response, listType)
                cont.resume(listCollectorPerformer)
            },{
                cont.resumeWithException(it)
                Log.e("error getting collector performers list", it.toString())
            })
        )
    }

}