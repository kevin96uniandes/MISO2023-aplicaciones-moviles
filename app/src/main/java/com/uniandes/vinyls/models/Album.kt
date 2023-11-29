package com.uniandes.vinyls.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "albums")
data class Album (
    @PrimaryKey(autoGenerate = true)
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    var tracks: List<Track> = mutableListOf<Track>()
    ) {

    constructor() : this(0, "", "", "", "", "", "")

    companion object {
        fun fromJSONObject(jsonObject: JSONObject): Album {
            val tracks: List<Track> = if (jsonObject.isNull("tracks")) {
                listOf()
            }else {
                Track.fromJSONArray(
                    jsonObject.getJSONArray("tracks")
                )
            }
            return Album(
                albumId = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                releaseDate = jsonObject.getString("releaseDate"),
                genre = jsonObject.getString("genre"),
                cover = jsonObject.getString("cover"),
                recordLabel = jsonObject.getString("recordLabel"),
                description = jsonObject.getString("description"),
                tracks = tracks
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
                album = fromJSONObject(jsonArray.getJSONObject(i))
                albumsArray.add(
                    album
                )
            }
            return albumsArray
        }

    }

    fun formatDateReelaseDate(): String {
        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val formatoSalida = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val fecha = formatoEntrada.parse(this.releaseDate)
            return formatoSalida.format(fecha)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun formatDateReelaseDate(): String {
        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val formatoSalida = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val fecha = formatoEntrada.parse(this.releaseDate)
            return formatoSalida.format(fecha)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun addTrack(track: Track){
        if (this.tracks.isEmpty()){
            this.tracks = emptyList()
        }
        this.tracks += track
    }
}