package com.juliaralves.unhaverso.data.local.room.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.juliaralves.unhaverso.data.local.room.dao.NailPolishDao
import com.juliaralves.unhaverso.data.local.room.entity.NailPolish

@Database(entities = [NailPolish::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nailPolishDao(): NailPolishDao
}