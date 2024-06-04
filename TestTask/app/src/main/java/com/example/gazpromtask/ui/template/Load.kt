package com.example.gazpromtask.ui.template

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gazpromtask.viewmodel.CitiesListViewModel
import com.example.gazpromtask.viewmodel.errorUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@Preview
@Composable
fun LoadScreen(
    onSuccessful: () -> Unit = {},
    onFailure: (String) -> Unit = {},
    viewModel: CitiesListViewModel = viewModel(factory=CitiesListViewModel.Factory)
) {
    val networkResult by viewModel.loadUiState.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchCities()
    }

    LaunchedEffect(networkResult) {
        if (networkResult != null) {
            val moMatter = networkResult?.message
            when(networkResult) {
                errorUiState(true, null) -> onSuccessful()
                errorUiState(false, moMatter) -> moMatter?.let { onFailure(it) }
            }
        }
    }

    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = Color(0xFF6750A4),
            modifier = Modifier
                .size(48.dp)
        )
    }
}