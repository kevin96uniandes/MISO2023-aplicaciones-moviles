package com.uniandes.vinyls.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinyls.models.CollectorPerformer

@Dao
interface CollectorPerformerDao {

    @Query("SELECT * FROM COLLECTOR_PERFORMERS WHERE collectorId = :collectorId")
    suspend fun findAll(collectorId: Int): List<CollectorPerformer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectorPerformer: CollectorPerformer)

}