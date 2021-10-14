package com.expl.jokegenerator.room

import androidx.lifecycle.LiveData
import com.expl.jokegenerator.model.Joke
import javax.inject.Inject

class AppRoomRepository @Inject constructor(private val appRoomDao: AppRoomDao) : DatabaseRepository {

    override val allJokes: LiveData<List<Joke>>
        get() = appRoomDao.getAllNotes()

    override suspend fun insert(joke: Joke, onSuccess: () -> Unit) {
        appRoomDao.insert(joke)
        onSuccess()
    }

    override suspend fun insertAll(jokes: List<Joke>, onSuccess: () -> Unit) {
        appRoomDao.insertAll(jokes)
        onSuccess()
    }

    override suspend fun deleteAll(jokes: List<Joke>, onSuccess: () -> Unit) {
        appRoomDao.deleteAll(jokes)
        onSuccess()
    }


    override suspend fun delete(joke: Joke, onSuccess: () -> Unit) {
        appRoomDao.delete(joke)
        onSuccess()
    }


}