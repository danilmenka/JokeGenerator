package com.expl.jokegenerator.network

import com.expl.jokegenerator.model.Joke
import com.expl.jokegenerator.model.SearchJokeResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    @GET("categories")
    suspend fun getCategories(
    ): Response<List<String>>

    @GET("random")
    suspend fun getRandomJoke(
    ): Response<Joke>

    @GET("random?")
    suspend fun getRandomJokeByCategory(
        @Query("category") category: String
    ): Response<Joke>

    @GET("search?")
    suspend fun getJokeSearch(
        @Query("query") query: String
    ): Response<SearchJokeResult>
}