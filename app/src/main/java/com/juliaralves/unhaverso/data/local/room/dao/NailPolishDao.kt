package com.juliaralves.unhaverso.data.local.room.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.juliaralves.unhaverso.data.local.room.entity.NailPolish

@Dao
interface NailPolishDao {
    @Query("SELECT * FROM nail_polish")
    suspend fun getAll(): List<NailPolish>

    @Query("SELECT * FROM nail_polish WHERE name LIKE :text OR brand LIKE :text")
    suspend fun findByText(text: String): List<NailPolish>

    @Query("SELECT * FROM nail_polish WHERE tagList LIKE :tagText")
    suspend fun findByTag(tagText: String): List<NailPolish>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nailPolish: NailPolish)

    @Delete
    suspend fun delete(nailPolish: NailPolish)

    @Query("DELETE FROM nail_polish")
    suspend fun clear()
}