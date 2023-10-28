package com.uniandes.vinyls.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uniandes.vinyls.models.Album


@Database(
    entities = [Album::class],
    version = 1
)
abstract class VinylDB : RoomDatabase() {
    abstract fun albumDao(): AlbumDAO
}