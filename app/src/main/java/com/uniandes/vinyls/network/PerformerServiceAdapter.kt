package com.uniandes.vinyls.network

import android.content.Context
import android.util.Log
import com.uniandes.vinyls.models.Performer
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class PerformerServiceAdapter (context: Context): NetworkServiceAdapter(context) {
    companion object {
        @Volatile
        private var instance: PerformerServiceAdapter? = null

        fun getInstance(context: Context): PerformerServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: PerformerServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }
    suspend fun findAll() = suspendCoroutine { continuation ->
        requestQueue.add(
            getRequest("/musicians", {response ->
                val responseJson = JSONArray(response)
                val listPerformers = Performer.fromJSONArray(responseJson)
                continuation.resume(listPerformers)
            },{
                continuation.resumeWithException(it)
                Log.e("error getting performer list", it.toString())
            })
        )
    }

    suspend fun getPerformerById(id: Int) = suspendCoroutine<Performer> { continuation ->
        requestQueue.add(
            getRequest("/musicians/${id}", {response ->
                val resp = JSONObject(response)
                val performer = Performer.fromJSONObject(resp)
                continuation.resume(performer)
            },{
                continuation.resumeWithException(it)
                Log.e("error =>", it.toString())
            })
        )
    }
}