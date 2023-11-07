package com.uniandes.vinyls.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "albums")
data class Album (
    @PrimaryKey(autoGenerate = true)
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String
) {

    companion object {
        private fun fromJSONObject(jsonObject: JSONObject): Album {
            return Album(
                albumId = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                releaseDate = jsonObject.getString("releaseDate"),
                genre = jsonObject.getString("genre"),
                cover = jsonObject.getString("cover"),
                recordLabel = jsonObject.getString("recordLabel"),
                description = jsonObject.getString("description")
            )
        }

        fun fromMap(map: Map<String, Any>): Album {
            return Album(
                albumId = 0,
                name = map.get("name").toString(),
                releaseDate = map.get("releaseDate").toString(),
                genre = map.get("genre").toString(),
                cover = map.get("cover").toString(),
                recordLabel = map.get("recordLabel").toString(),
                description = map.get("description").toString()
            )
        }

        fun fromJSONArray(jsonArray: JSONArray): List<Album> {
            val albumsArray = mutableListOf<Album>()
            var album: Album? = null
            for (i in 0 until jsonArray.length()) {
                album = Album.fromJSONObject(jsonArray.getJSONObject(i))
                albumsArray.add(
                    album
                )
            }
            return albumsArray
        }
    }
}