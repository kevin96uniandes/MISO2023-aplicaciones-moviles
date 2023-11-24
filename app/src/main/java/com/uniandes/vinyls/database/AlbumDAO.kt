
package com.uniandes.vinyls.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uniandes.vinyls.models.Album

@Dao
interface AlbumDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: Album)

    @Update
    suspend fun update(album: Album)

    @Query("SELECT * FROM albums ORDER BY albumId ASC")
    fun getAlbums(): List<Album>

    @Query("DELETE FROM albums")
    suspend fun deleteAll(): Int

    @Query("SELECT COUNT(*) FROM albums")
    fun countAlbums(): Int

    @Query("SELECT * FROM albums WHERE albumId = :albumId")
    fun getAlbumById(albumId: Int): Album?
}