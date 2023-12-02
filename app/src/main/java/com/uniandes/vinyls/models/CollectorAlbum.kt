package com.uniandes.vinyls.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.json.JSONArray
import org.json.JSONObject

@Entity("collector_albums",
indices = [Index(value = ["collectorId"], unique = false)])
data class CollectorAlbum(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "collectorId")
    val collectorId: Int,
    val price: Double,
    val album: Album
){

    companion object {

        fun fromJSONObject(jsonObject: JSONObject, collectorId: Int): CollectorAlbum  {
            val album = if (jsonObject.isNull("album")){
                Album()
            }else {
                Album.fromJSONObject(jsonObject.getJSONObject("album"))
            }
            return CollectorAlbum(
                id = jsonObject.getInt(("id")),
                price = jsonObject.getDouble("price"),
                album = album,
                collectorId = collectorId
            )
        }


        fun fromJSONArray(jsonArray: JSONArray, collectorId: Int): List<CollectorAlbum> {
            val collectorAlbums = mutableListOf<CollectorAlbum>()
            for (i in 0 until jsonArray.length()) {
                collectorAlbums.add(
                    fromJSONObject(jsonArray.getJSONObject(i), collectorId)
                )
            }
            return collectorAlbums
        }
    }
}