
package com.uniandes.vinyls.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinyls.models.Album
import com.uniandes.vinyls.models.Track

@Dao
interface TracksDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(track: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: Track)

    @Query("SELECT * FROM tracks_table ORDER BY id ASC")
    fun getTracks(): List<Track>

    @Query("DELETE FROM tracks_table")
    suspend fun deleteAll(): Int

    @Query("SELECT COUNT(*) FROM tracks_table")
    fun countTracks(): Int

    @Query("SELECT * FROM tracks_table WHERE id = :trackId")
    fun getTracksById(trackId: Int): Track?
}