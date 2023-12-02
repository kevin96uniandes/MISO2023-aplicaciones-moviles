package com.uniandes.vinyls.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

@Entity("collector_performers",
    indices = [Index(value = ["collectorId"], unique = false)])
data class CollectorPerformer(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "collectorId")
    val collectorId: Int,
    val name: String,
    val image: String,
    val description: String
) {

    companion object {

        fun fromJSONObject(jsonObject: JSONObject, collectorId: Int): CollectorPerformer  {
            return CollectorPerformer(
                id = jsonObject.getInt(("id")),
                name = jsonObject.getString("name"),
                image = jsonObject.getString("image"),
                description = jsonObject.getString("description"),
                collectorId = collectorId
            )
        }


        fun fromJSONArray(jsonArray: JSONArray, collectorId: Int): List<CollectorPerformer> {
            val collectorPerformers = mutableListOf<CollectorPerformer>()
            for (i in 0 until jsonArray.length()) {
                collectorPerformers.add(
                    fromJSONObject(jsonArray.getJSONObject(i), collectorId)
                )
            }
            return collectorPerformers
        }
    }

}