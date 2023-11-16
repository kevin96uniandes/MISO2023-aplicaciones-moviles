package com.uniandes.vinyls.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
}