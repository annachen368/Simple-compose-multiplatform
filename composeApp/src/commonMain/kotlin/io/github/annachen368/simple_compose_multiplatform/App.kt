package io.github.annachen368.simple_compose_multiplatform

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.annachen368.simple_compose_multiplatform.ui.CatScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        CatScreen()
    }
}