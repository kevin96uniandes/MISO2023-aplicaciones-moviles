package com.uniandes.vinyls.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uniandes.vinyls.database.converters.DataConverter
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.models.Track
import com.uniandes.vinyls.models.Collector
import com.uniandes.vinyls.models.CollectorAlbum
import com.uniandes.vinyls.models.CollectorPerformer
import com.uniandes.vinyls.models.Performer

@Database(
    entities = [Album::class, Collector::class, Performer::class,Track::class, CollectorAlbum::class, CollectorPerformer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class VinylDB : RoomDatabase() {
    abstract fun albumDao(): AlbumDAO
    abstract fun collectorDao(): CollectorDAO
    abstract fun performerDao(): PerformerDAO
    abstract fun collectorAlbumDao(): CollectorAlbumDAO
    abstract fun collectorPerformerDao(): CollectorPerformerDao

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