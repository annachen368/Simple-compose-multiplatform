package io.github.annachen368.simple_compose_multiplatform.data.repository

import io.github.annachen368.simple_compose_multiplatform.data.api.CatApi
import io.github.annachen368.simple_compose_multiplatform.data.model.Cat
import io.github.annachen368.simple_compose_multiplatform.data.storage.CatStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.io.IOException

interface CatRepository {
    suspend fun refresh()
    fun getCats(): Flow<List<Cat>>
}

class CatRepositoryImpl(
    private val catApi: CatApi,
    private val catStorage: CatStorage
) : CatRepository {

    override suspend fun refresh() {
        catApi.fetchCatResponse().onSuccess {
            catStorage.saveCats(it)
        }.onFailure { e ->
            throw when (e) {
                is IOException -> CatError.NetworkError
                else -> CatError.UnknownError
            }
        }
    }

    override fun getCats(): Flow<List<Cat>> {
        return catStorage.getCats()
    }
}

sealed class CatError(message: String) : Exception(message) {
    object NetworkError : CatError("Network Error")
    object UnknownError : CatError("Unknown Error")
}