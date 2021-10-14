package com.expl.jokegenerator.di

import android.content.Context
import com.expl.jokegenerator.room.AppRoomDao
import com.expl.jokegenerator.room.AppRoomDatabase
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
    fun provideDataBase(@ApplicationContext context: Context): AppRoomDatabase {
        return AppRoomDatabase.getInstance(context)
    }

    @Provides
    fun provideAppRoomDao(database: AppRoomDatabase): AppRoomDao {
        return database.getAppRoomDao()
    }
}