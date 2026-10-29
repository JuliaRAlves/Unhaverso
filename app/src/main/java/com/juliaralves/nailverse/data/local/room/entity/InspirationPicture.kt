package com.juliaralves.nailverse.data.local.room.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "inspiration_picture")
data class InspirationPicture(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0L,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "query") val query: String
)