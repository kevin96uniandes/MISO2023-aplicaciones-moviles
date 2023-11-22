package com.uniandes.vinyls.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "collectors")
data class Collector (
    @PrimaryKey(autoGenerate = true) val collectorId: Int,
    val name: String,
    val telephone: String,
    val email: String,
    var createdAt: Long = System.currentTimeMillis()
): Parcelable {

    constructor(parcel: Parcel) : this(
        collectorId = parcel.readInt(),
        name = parcel.readString() ?: "",
        telephone = parcel.readString() ?: "",
        email = parcel.readString() ?: ""
    )

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Collector> = object : Parcelable.Creator<Collector> {
            override fun createFromParcel(parcel: Parcel): Collector {
                return Collector(parcel)
            }

            override fun newArray(size: Int): Array<Collector?> {
                return arrayOfNulls(size)
            }
        }

        fun fromMap(map: Map<String, Any>): Collector {
            return Collector(
                collectorId = 0,
                name = map["name"].toString(),
                telephone = map["telephone"].toString(),
                email = map["email"].toString()
            )
        }

        private fun fromJSONObject(jsonObject: JSONObject): Collector {
            return Collector(
                collectorId = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                telephone = jsonObject.getString("telephone"),
                email = jsonObject.getString("email")
            )
        }

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

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(collectorId)
        parcel.writeString(name)
        parcel.writeString(telephone)
        parcel.writeString(email)
    }
}