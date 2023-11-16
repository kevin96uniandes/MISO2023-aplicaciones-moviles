package com.uniandes.vinyls.network

import android.content.Context
import android.util.Log
import com.uniandes.vinyls.models.Collector
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectorServiceAdapter(context: Context): NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: CollectorServiceAdapter? = null

        fun getInstance(context: Context): CollectorServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: CollectorServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    suspend fun findAll() = suspendCoroutine { continuation ->
        requestQueue.add(
            getRequest("/collectors", {response ->
                val responseJson = JSONArray(response)
                val listCollectors = Collector.fromJSONArray(responseJson)
                continuation.resume(listCollectors)
            },{
                continuation.resumeWithException(it)
                Log.e("error getting collector list", it.toString())
            })
        )
    }
}