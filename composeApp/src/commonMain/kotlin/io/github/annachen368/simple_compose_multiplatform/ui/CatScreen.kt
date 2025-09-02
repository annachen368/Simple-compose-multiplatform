package io.github.annachen368.simple_compose_multiplatform.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.annachen368.simple_compose_multiplatform.data.model.Cat
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CatScreen() {
    val viewModel = koinViewModel<CatViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        CatUiState.Loading -> {
            CatScreenLoading()
        }

        CatUiState.Empty -> {
            CatScreenEmpty()
        }

        is CatUiState.Success -> {
            CatScreenSuccess((uiState as CatUiState.Success).list)
        }

        is CatUiState.Error -> {
            CatScreenError((uiState as CatUiState.Error).message)
        }
    }
}

@Composable
fun CatScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CatScreenEmpty() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No Cats available")
    }
}

@Composable
fun CatScreenSuccess(cats: List<Cat>) {
    LazyColumn {
        items(cats) {
            CatItem(it)
        }
    }
}

@Composable
fun CatItem(cat: Cat) {
    Column {
        Text(text = "#${cat.id}")
        Text(text = cat.name)
        Text(text = cat.gender)
        Text(text = "${cat.age} yo")
        Text(text = "${cat.adopted}")
        cat.imageUrl?.let {
            Text(text = it)
        }
        HorizontalDivider()
    }
}

@Composable
fun CatScreenError(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}