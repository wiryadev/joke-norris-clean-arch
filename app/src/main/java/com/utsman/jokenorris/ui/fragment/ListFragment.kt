package com.utsman.jokenorris.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.composethemeadapter.MdcTheme
import com.utsman.jokenorris.R
import com.utsman.jokenorris.domain.ResultState
import com.utsman.jokenorris.domain.entity.Joke
import com.utsman.jokenorris.ui.components.ItemNetwork
import com.utsman.jokenorris.ui.components.JokeList
import com.utsman.jokenorris.ui.viewModel.JokeViewModel
import com.utsman.jokenorris.utils.onFailure
import com.utsman.jokenorris.utils.onSuccess
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: JokeViewModel by activityViewModels()
    private val resultState = mutableStateOf<ResultState<List<Joke>>>(ResultState.Idle())
    private val jokesList = mutableListOf<Joke>()
    private val errorMessage = mutableStateOf("")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            viewModel.list.observe(viewLifecycleOwner) { result ->
                resultState.value = result

                result.onFailure {
                    errorMessage.value = it.message.toString()
                }

                result.onSuccess { jokes ->
                    jokesList.clear()
                    jokesList.addAll(jokes)
                }
            }

            setContent {
                MdcTheme(
                    setDefaultFontFamily = true
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (resultState.value is ResultState.Success) {
                            JokeList(list = jokesList)
                        }

                        ItemNetwork(
                            isLoading = resultState.value is ResultState.Loading,
                            isError = resultState.value is ResultState.Error,
                            errorMessage = errorMessage.value,
                            retryOnClick = { viewModel.getList() },
                            modifier = Modifier.align(Alignment.Center)
                        )

                        FloatingActionButton(
                            onClick = { viewModel.getList() },
                            backgroundColor = Color.White,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(34.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_shuffle),
                                contentDescription = stringResource(
                                    id = R.string.decs_refresh_the_jokes
                                ),
                                colorFilter = ColorFilter.tint(Color.Gray)
                            )
                        }
                    }
                }
            }
        }
    }
}