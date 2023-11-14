package com.uniandes.vinyls.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinyls.models.Performer
@Dao
interface PerformerDAO {
    @Query("SELECT * FROM performers ORDER BY name ASC")
    fun findAll(): List<Performer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(performer: Performer)

    @Query("SELECT COUNT(*) FROM performers")
    fun countPerformers(): Int
}