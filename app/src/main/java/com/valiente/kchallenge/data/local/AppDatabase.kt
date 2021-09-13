package com.valiente.kchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valiente.kchallenge.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}