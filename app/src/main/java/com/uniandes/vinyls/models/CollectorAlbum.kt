package com.uniandes.vinyls.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.json.JSONArray
import org.json.JSONObject

@Entity("collector_albums")
data class CollectorAlbum(
    @PrimaryKey
    val id: Int,
    val price: Double,
    val status: CollectorAlbumStatus,
    val album: Album
){
    enum class CollectorAlbumStatus(status: String) {
        ACTIVE("Active"),
        INACTIVE("Inactive")
    }

    companion object {

        fun fromJSONObject(jsonObject: JSONObject): CollectorAlbum  =
            CollectorAlbum(
                id = jsonObject.getInt(("id")),
                price = jsonObject.getDouble("price"),
                status = CollectorAlbumStatus.valueOf(jsonObject.getString("status")),
                album = jsonObject.get("album") as Album
            )

        fun fromJSONArray(jsonArray: JSONArray): List<CollectorAlbum> {
            val collectorAlbums = mutableListOf<CollectorAlbum>()
            for (i in 0 until jsonArray.length()) {
                collectorAlbums.add(
                    fromJSONObject(jsonArray.getJSONObject(i))
                )
            }
            return collectorAlbums
        }
    }
}