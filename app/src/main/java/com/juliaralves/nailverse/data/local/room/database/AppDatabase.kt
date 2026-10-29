package com.juliaralves.nailverse.data.local.room.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.juliaralves.nailverse.data.local.room.dao.InspirationPictureDao
import com.juliaralves.nailverse.data.local.room.dao.NailPolishDao
import com.juliaralves.nailverse.data.local.room.entity.InspirationPicture
import com.juliaralves.nailverse.data.local.room.entity.NailPolish

@Database(entities = [NailPolish::class, InspirationPicture::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nailPolishDao(): NailPolishDao
    abstract fun inspirationPictureDao(): InspirationPictureDao
}