package com.juliaralves.unhaverso.data.local.room.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "nail_polish")
data class NailPolish(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0L,
    @ColumnInfo(name = "hexColor") val hexColor: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "brand") val brand: String,
    @ColumnInfo(name = "tagList") val tagList: String,
    @ColumnInfo(name = "createdAt") val createdAt: Long
)