package com.utsman.jokenorris.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.utsman.jokenorris.domain.entity.Joke

@Composable
fun JokeList(list: List<Joke>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(list) { joke ->
            ItemJoke(text = joke.joke)
        }
    }
}