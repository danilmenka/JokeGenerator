package com.expl.jokegenerator.network

import androidx.lifecycle.MutableLiveData
import com.expl.jokegenerator.Event
import com.expl.jokegenerator.R
import com.expl.jokegenerator.model.Joke
import com.expl.jokegenerator.model.SearchJokeResult
import com.expl.jokegenerator.utilits.APP_ACTIVITY
import javax.inject.Inject

class NetworkRepository @Inject constructor (private val remoteService: RemoteService) {

    suspend fun getCategories(categoriesLiveData:MutableLiveData<Event<List<String>>>){
        ResponseSigner.startResponse(categoriesLiveData){
            remoteService.getCategories()
        }
    }

    suspend fun getRandomJokeByCategory
                (randomJokeLivedata:MutableLiveData<Event<Joke>>,category:String){
        ResponseSigner.startResponse(randomJokeLivedata){
            if (category == APP_ACTIVITY.getString(R.string.emptySpinner))
                remoteService.getRandomJoke()
            else
                remoteService.getRandomJokeByCategory(category)
        }
    }

    suspend fun getSearchResult(searchResultLiveData:MutableLiveData<Event<SearchJokeResult>>, query: String){
        ResponseSigner.startResponse(searchResultLiveData) {
            remoteService.getJokeSearch(query)
        }
    }


}