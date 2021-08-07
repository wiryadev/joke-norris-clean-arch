package com.utsman.jokenorris.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.utsman.jokenorris.R

@Composable
fun ItemNetwork(
    isLoading: Boolean,
    isError: Boolean,
    errorMessage: String,
    retryOnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }
        if (isError) {
            Text(
                text = errorMessage
            )
            Button(onClick = retryOnClick) {
                Text(text = stringResource(id = R.string.try_again))
            }
        }
    }
}