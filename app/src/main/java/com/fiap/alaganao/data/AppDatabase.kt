package com.fiap.alaganao.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Relato::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun relatoDao(): RelatoDao
}
