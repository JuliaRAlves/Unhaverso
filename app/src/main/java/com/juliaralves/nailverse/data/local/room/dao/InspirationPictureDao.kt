package com.juliaralves.nailverse.data.local.room.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.juliaralves.nailverse.data.local.room.entity.InspirationPicture

@Dao
interface InspirationPictureDao {
    @Query("SELECT * FROM inspiration_picture ")
    suspend fun getAll(): List<InspirationPicture>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(picture: InspirationPicture)

    @Query("DELETE FROM inspiration_picture")
    suspend fun clear()
}