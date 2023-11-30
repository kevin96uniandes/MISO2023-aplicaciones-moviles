package com.uniandes.vinyls.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.models.CollectorAlbum
import com.uniandes.vinyls.models.Track

class DataConverter {

    @TypeConverter
    fun fromTracksList(value: List<Track>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Track>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTracksList(value: String): List<Track> {
        val gson = Gson()
        val type = object : TypeToken<List<Track>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromAlbum(album: Album): String {
        return Gson().toJson(album)
    }

    @TypeConverter
    fun toAlbum(albumString: String): Album {
        return Gson().fromJson(albumString, Album::class.java)
    }

    @TypeConverter
    fun fromAlbumList(value: List<Album>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Album>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAlbumList(value: String): List<Album> {
        val gson = Gson()
        val type = object : TypeToken<List<Album>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCollectorAlbumList(value: List<CollectorAlbum>): String {
        val gson = Gson()
        val type = object : TypeToken<List<CollectorAlbum>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCollectorAlbumList(value: String): List<CollectorAlbum> {
        val gson = Gson()
        val type = object : TypeToken<List<CollectorAlbum>>() {}.type
        return gson.fromJson(value, type)
    }
}