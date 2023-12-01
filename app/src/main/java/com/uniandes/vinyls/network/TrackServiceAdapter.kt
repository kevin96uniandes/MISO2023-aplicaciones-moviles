package com.uniandes.vinyls.network

import android.content.Context
import android.util.Log
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.models.Track
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TrackServiceAdapter(context: Context): NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: TrackServiceAdapter? = null

        fun getInstance(context: Context): TrackServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: TrackServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    suspend fun asociateTrack(track: Map<String, Any>, id: Int) = suspendCoroutine<Track>{ continuation ->
        requestQueue.add(
            postRequest("/albums/${id}/tracks", JSONObject(track), { response ->
                val track = Track(
                    id = response.getInt("id"),
                    name = response.getString("name"),
                    duration = response.getString("duration") )
                continuation.resume(track)
            },{
                continuation.resumeWithException(it)
                Log.e("error => ", it.toString())
            })
        )
    }
}