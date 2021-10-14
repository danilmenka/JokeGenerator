package com.expl.jokegenerator.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchJokeResult(
    @SerializedName("total") var total : Int,
    @SerializedName("result") var jokesList:List<Joke>
) : Serializable