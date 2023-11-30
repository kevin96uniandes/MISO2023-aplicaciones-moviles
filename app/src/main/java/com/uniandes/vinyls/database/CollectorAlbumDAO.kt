package com.uniandes.vinyls.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinyls.models.CollectorAlbum

@Dao
interface CollectorAlbumDAO {

    @Query("SELECT * FROM COLLECTOR_ALBUMS WHERE collectorId = :collectorId")
    suspend fun findAll(collectorId: Int): List<CollectorAlbum>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectorAlbum: CollectorAlbum)
}