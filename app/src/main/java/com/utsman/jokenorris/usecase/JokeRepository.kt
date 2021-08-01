package com.utsman.jokenorris.usecase

import com.utsman.jokenorris.domain.ResultState
import com.utsman.jokenorris.domain.entity.Joke
import kotlinx.coroutines.flow.StateFlow

interface JokeRepository {
    val categories: StateFlow<ResultState<List<String>>>
    val random: StateFlow<ResultState<Joke>>
    val list: StateFlow<ResultState<List<Joke>>>
    val search: StateFlow<ResultState<List<Joke>>>

    suspend fun getCategories()
    suspend fun getRandom(category: String = "")
    suspend fun getList(size: Int = 5)
    suspend fun getSearch(query: String)
}