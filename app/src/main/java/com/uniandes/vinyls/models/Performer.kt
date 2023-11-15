package com.uniandes.vinyls.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "performers")
data class Performer (
    @PrimaryKey(autoGenerate = true)
    val performerId:Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String
) {

    companion object {

        fun fromMap(map: Map<String, Any>): Performer {
            return Performer(
                performerId = 0,
                name = map["name"].toString(),
                image = map["image"].toString(),
                description = map["description"].toString(),
                birthDate = map["birthDate"].toString()
            )
        }

        private fun fromJSONObject(jsonObject: JSONObject): Performer{
            return Performer(
                performerId = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                image = jsonObject.getString("image"),
                description = jsonObject.getString("description"),
                birthDate = jsonObject.getString("birthDate")
            )
        }

        fun fromJSONArray(jsonArray: JSONArray): List<Performer> {
            val performerArray = mutableListOf<Performer>()
            var performer: Performer? = null
            for (i in 0 until jsonArray.length()) {
                performer = fromJSONObject(jsonArray.getJSONObject(i))
                performerArray.add(
                    performer
                )
            }
            return performerArray
        }
    }
}