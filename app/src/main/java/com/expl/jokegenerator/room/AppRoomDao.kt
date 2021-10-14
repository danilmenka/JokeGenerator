package com.expl.jokegenerator.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.expl.jokegenerator.model.Joke

@Dao
interface AppRoomDao {

    @Query("SELECT * from joke_table")
    fun getAllNotes(): LiveData<List<Joke>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(joke: Joke)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(jokes: List<Joke>)

    @Delete
    suspend fun delete(joke: Joke)

    @Delete
    suspend fun deleteAll(jokes: List<Joke>)

}