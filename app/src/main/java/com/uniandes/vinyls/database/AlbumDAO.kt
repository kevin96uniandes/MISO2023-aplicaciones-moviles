package com.uniandes.vinyls.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinyls.models.Album

@Dao
interface AlbumDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(album: Album)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll(): Int

}