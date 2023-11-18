package com.uniandes.vinyls.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinyls.models.Collector

@Dao
interface CollectorDAO {

    @Query("SELECT * FROM collectors ORDER BY name ASC")
    fun findAll(): List<Collector>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collector: Collector)

    @Query("SELECT COUNT(*) FROM collectors")
    fun countCollectors(): Int

}