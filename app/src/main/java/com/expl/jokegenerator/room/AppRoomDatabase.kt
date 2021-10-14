package com.expl.jokegenerator.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.expl.jokegenerator.model.Joke
import com.expl.jokegenerator.utilits.DATABASE_NAME

@Database(entities = [Joke::class],version = 1)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun getAppRoomDao():AppRoomDao

    companion object{

        @Volatile
        private var database:AppRoomDatabase?=null

        @Synchronized
        fun getInstance(context: Context):AppRoomDatabase{
            return if (database==null){
                database = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    DATABASE_NAME
                ).build()
                database as AppRoomDatabase
            } else database as AppRoomDatabase
        }
    }
}