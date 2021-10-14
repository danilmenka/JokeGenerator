package com.expl.jokegenerator.room

import androidx.lifecycle.LiveData
import com.expl.jokegenerator.model.Joke

interface DatabaseRepository {
    val allJokes: LiveData<List<Joke>>
    suspend fun insert(joke: Joke, onSuccess: () -> Unit)
    suspend fun delete(joke: Joke, onSuccess: () -> Unit)
    suspend fun insertAll(jokes: List<Joke>, onSuccess: () -> Unit)
    suspend fun deleteAll(jokes: List<Joke>, onSuccess: () -> Unit)
}