package com.uniandes.vinyls.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.models.CollectorAlbum
import org.json.JSONArray
import org.json.JSONObject
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
                //val gson = Gson()
                //val listType = object : TypeToken<List<Collector>>() {}.type
                //val listCollectors: List<Collector> = gson.fromJson(response, listType)
                val listCollectors = Collector.fromJSONArray(responseJson)
                continuation.resume(listCollectors)
            },{
                continuation.resumeWithException(it)
                Log.e("error getting collector list", it.toString())
            })
        )
    }

    suspend fun findById(collectorId: Int) = suspendCoroutine { continuation ->
        requestQueue.add(
            getRequest("/collectors/${collectorId}", {response ->
                //val gson = Gson()
                //val collector: Collector = gson.fromJson(response, Collector::class.java)
                val resp = JSONObject(response)
                val collector = Collector.fromJSONObject(resp)
                continuation.resume(collector)
            },{
                continuation.resumeWithException(it)
                Log.e("error getting collector list", it.toString())
            })
        )
    }
}