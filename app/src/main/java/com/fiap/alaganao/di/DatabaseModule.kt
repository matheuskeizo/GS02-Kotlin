package com.fiap.alaganao.di

import android.content.Context
import androidx.room.Room
import com.fiap.alaganao.data.AppDatabase
import com.fiap.alaganao.data.RelatoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "alaga-nao.db").build()

    @Provides
    @Singleton
    fun provideRelatoDao(database: AppDatabase): RelatoDao = database.relatoDao()
}
