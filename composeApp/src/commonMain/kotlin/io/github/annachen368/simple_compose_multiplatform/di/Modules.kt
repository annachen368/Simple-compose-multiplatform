package io.github.annachen368.simple_compose_multiplatform.di

import io.github.annachen368.simple_compose_multiplatform.data.api.CatApi
import io.github.annachen368.simple_compose_multiplatform.data.api.KtorCatApiImpl
import io.github.annachen368.simple_compose_multiplatform.data.repository.CatRepository
import io.github.annachen368.simple_compose_multiplatform.data.repository.CatRepositoryImpl
import io.github.annachen368.simple_compose_multiplatform.data.storage.CatStorage
import io.github.annachen368.simple_compose_multiplatform.data.storage.InMemoryCatStorage
import io.github.annachen368.simple_compose_multiplatform.ui.CatViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        useAlternativeNames = false
                    },
                    contentType = ContentType.Any
                )
            }
        }
    }

    single<CatApi> { KtorCatApiImpl(get()) }

    single<CatStorage> { InMemoryCatStorage() }

    single<CatRepository> {
        CatRepositoryImpl(get(), get())
    }
}

val viewModelModule = module {
    viewModel { CatViewModel(get()) }
}

fun initKoin() {
    startKoin {
        modules(dataModule, viewModelModule)
    }
}