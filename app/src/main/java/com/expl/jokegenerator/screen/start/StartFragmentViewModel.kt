package com.expl.jokegenerator.screen.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expl.jokegenerator.Event
import com.expl.jokegenerator.model.Joke
import com.expl.jokegenerator.model.SearchJokeResult
import com.expl.jokegenerator.network.NetworkRepository
import com.expl.jokegenerator.room.AppRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartFragmentViewModel @Inject constructor
                (private val networkRepository: NetworkRepository,
                 private val dbRepository: AppRoomRepository):ViewModel() {

    val savedJokes = dbRepository.allJokes
    val randomJokeLivedata = MutableLiveData<Event<Joke>>()
    val categoriesLiveData = MutableLiveData<Event<List<String>>>()
    var searchResultLiveData = MutableLiveData<Event<SearchJokeResult>>()

    fun getCategories(){
        this.viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getCategories(categoriesLiveData)
        }
    }

    fun getRandomJokeByCategory(category: String){
        this.viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getRandomJokeByCategory(randomJokeLivedata, category)
        }
    }

    fun getSearchResult(query: String){
        this.viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getSearchResult(searchResultLiveData, query)
        }
    }

    fun saveJoke(joke: Joke, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insert(joke){onSuccess()}
        }

    fun delete(joke: Joke, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.delete(joke) {onSuccess()}
        }

    fun removeSearchResult(){
        searchResultLiveData = MutableLiveData<Event<SearchJokeResult>>()
    }

}