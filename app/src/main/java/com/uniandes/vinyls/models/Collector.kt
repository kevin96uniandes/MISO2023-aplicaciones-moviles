package com.uniandes.vinyls.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "collectors")
data class Collector (
    @PrimaryKey(autoGenerate = true) val collectorId: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val albums: List<CollectorAlbum> = mutableListOf(),
    var createdAt: Long = System.currentTimeMillis()
) {

    companion object {

        fun fromJSONObject(jsonObject: JSONObject): Collector {
            return Collector(
                collectorId = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                telephone = jsonObject.getString("telephone"),
                email = jsonObject.getString("email"),
                albums = CollectorAlbum.fromJSONArray(
                    jsonObject.getJSONArray("albums")
                )
            )
        }

        fun fromMap(map: Map<String, Any>): Collector =
            Collector(
                collectorId = 0,
                name = map.get("name").toString(),
                telephone = map.get("telephone").toString(),
                email = map.get("email").toString(),
            )

        fun fromJSONArray(jsonArray: JSONArray): List<Collector> {
            val collectorArray = mutableListOf<Collector>()
            var collector: Collector? = null
            for (i in 0 until jsonArray.length()) {
                collector = fromJSONObject(jsonArray.getJSONObject(i))
                collectorArray.add(
                    collector
                )
            }
            return collectorArray
        }
    }


}