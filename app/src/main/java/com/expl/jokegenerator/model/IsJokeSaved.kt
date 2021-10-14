package com.expl.jokegenerator.model

data class IsJokeSaved(
    var joke : Joke,
    var saved: Boolean = false
)
