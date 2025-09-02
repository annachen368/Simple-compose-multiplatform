package io.github.annachen368.simple_compose_multiplatform.data.api

import io.github.annachen368.simple_compose_multiplatform.data.model.Cat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface CatApi {
    suspend fun fetchCatResponse(): Result<List<Cat>>
}

class KtorCatApiImpl(private val httpClient: HttpClient) : CatApi {

    companion object Companion {
        private const val API_URL =
            "https://raw.githubusercontent.com/annachen368/Simple-compose-multiplatform/refs/heads/main/cat.json"
    }

    override suspend fun fetchCatResponse(): Result<List<Cat>> {
        return try {
            Result.success(httpClient.get(API_URL).body())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}