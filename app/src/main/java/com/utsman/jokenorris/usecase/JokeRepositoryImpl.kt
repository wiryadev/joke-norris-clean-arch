package com.utsman.jokenorris.usecase

import com.utsman.jokenorris.domain.Mapper
import com.utsman.jokenorris.domain.ResultState
import com.utsman.jokenorris.domain.entity.Joke
import com.utsman.jokenorris.data.source.JokeDataSource
import com.utsman.jokenorris.utils.fetch
import com.utsman.jokenorris.utils.idle
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(private val dataSource: JokeDataSource) : JokeRepository {

    private val _categories: MutableStateFlow<ResultState<List<String>>> = idle()
    private val _random: MutableStateFlow<ResultState<Joke>> = idle()
    private val _list: MutableStateFlow<ResultState<List<Joke>>> = idle()
    private val _search: MutableStateFlow<ResultState<List<Joke>>> = idle()

    override val categories: StateFlow<ResultState<List<String>>> = _categories
    override val random: StateFlow<ResultState<Joke>> = _random
    override val list: StateFlow<ResultState<List<Joke>>> = _list
    override val search: StateFlow<ResultState<List<Joke>>> = _search

    override suspend fun getCategories() {
        fetch {
            dataSource.categories()
        }.collect {
            _categories.value = it
        }
    }

    override suspend fun getRandom(category: String) {
        fetch {
            dataSource.random(category)
        }.map {
            Mapper.mapResult(it) { Mapper.mapApiToEntity(this) }
        }.collect {
            _random.value = it
        }
    }

    override suspend fun getList(size: Int) {
        fetch {
            dataSource.list(size).distinctBy { it.value }
        }.map {
            Mapper.mapResult(it) {
                map { response ->
                    Mapper.mapApiToEntity(response)
                }
            }
        }.collect {
            _list.value = it
        }
    }

    override suspend fun getSearch(query: String) {
        fetch {
            dataSource.search(query)
        }.map {
            Mapper.mapResult(it) {
                result.map { joke ->
                    Mapper.mapApiToEntity(joke)
                }
            }
        }.collect {
            _search.value = it
        }
    }
}