package com.expl.jokegenerator.screen.saved

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expl.jokegenerator.model.Joke
import com.expl.jokegenerator.room.AppRoomDatabase
import com.expl.jokegenerator.room.AppRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedJokeFragmentViewModel @Inject constructor
        (private val dbRepository: AppRoomRepository): ViewModel() {

    val allJokes = dbRepository.allJokes

    fun delete(joke: Joke, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.delete(joke) {
                onSuccess()
            }
        }

    fun deleteAll(jokes: List<Joke>, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteAll(jokes) {
                onSuccess()
            }
        }
}