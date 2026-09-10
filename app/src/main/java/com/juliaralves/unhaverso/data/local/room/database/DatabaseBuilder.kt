package com.juliaralves.unhaverso.data.local.room.database

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun getDatabaseBuilder(context: Context): AppDatabase {
    return Room.databaseBuilder<AppDatabase>(context, "unhaverso.db")
        .setDriver(AndroidSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}