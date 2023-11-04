package com.uniandes.vinyls.network

import android.content.Context
import android.util.Log
import com.uniandes.vinyls.models.Album
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AlbumServiceAdapter(context: Context): NetworkServiceAdapter(context) {

    companion object {
        @Volatile
        private var instance: AlbumServiceAdapter? = null

        fun getInstance(context: Context): AlbumServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: AlbumServiceAdapter(context.applicationContext).also { instance = it }
            }
        }
    }

    suspend fun createAlbum(album: Map<String, Any>) = suspendCoroutine<Album>{ continuation ->
        requestQueue.add(
            postRequest("/albums", JSONObject(album), { response ->
                val createdAlbum = Album(
                    albumId = response.getInt("id"),
                    name = response.getString("name"),
                    releaseDate = response.getString("releaseDate"),
                    genre = response.getString("genre"),
                    cover = response.getString("cover"),
                    recordLabel = response.getString("recordLabel"),
                    description = response.getString("description"))
                continuation.resume(createdAlbum)
            },{
                continuation.resumeWithException(it)
                Log.e("error => ", it.toString())
            })
        )
    }

    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ continuation ->
        requestQueue.add(
            getRequest("/albums", { response ->
                val responseJson = JSONArray(response)
                val listAlbum = Album.fromJSONArray(responseJson)
                continuation.resume(listAlbum)
            },{
                continuation.resumeWithException(it)
                Log.e("error => ", it.toString())
            })
        )
    }

}