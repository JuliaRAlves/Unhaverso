package com.juliaralves.unhaverso.data.local.room.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.juliaralves.unhaverso.data.local.room.entity.NailPolish

@Dao
interface NailPolishDao {
    @Query(
        "SELECT * FROM nail_polish " +
                "WHERE :text IS NULL\n" +
                "    OR (\n" +
                "        name LIKE '%' || :text || '%'\n" +
                "        OR brand LIKE '%' || :text || '%'\n" +
                "        OR tagList LIKE '%' || :text || '%'\n" +
                "    )"
    )
    suspend fun getFilteredBy(text: String? = null): List<NailPolish>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nailPolish: NailPolish)

    @Query("DELETE FROM nail_polish WHERE :id = uid")
    suspend fun delete(id: Long)

    @Query("DELETE FROM nail_polish")
    suspend fun clear()
}