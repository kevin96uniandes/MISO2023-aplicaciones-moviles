package com.uniandes.vinyls.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.models.Collector


@Database(
    entities = [Album::class, Collector::class],
    version = 1,
    exportSchema = false
)
abstract class VinylDB : RoomDatabase() {
    abstract fun albumDao(): AlbumDAO
    abstract fun collectorDao(): CollectorDAO
    abstract fun performerDao(): PerformerDAO
    companion object {

        @Volatile
        private var INSTANCE: VinylDB? = null

        fun getDatabase(context: Context): VinylDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylDB::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}