package io.github.annachen368.simple_compose_multiplatform.data.storage

import io.github.annachen368.simple_compose_multiplatform.data.model.Cat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update

interface CatStorage {
    suspend fun saveCats(newCats: List<Cat>)
    fun getCats(): Flow<List<Cat>>
}

class InMemoryCatStorage : CatStorage {
    private val cats = MutableStateFlow<List<Cat>?>(null)

    override suspend fun saveCats(newCats: List<Cat>) {
        cats.update { newCats }
    }

    override fun getCats(): Flow<List<Cat>> {
        return cats.filterNotNull()
    }
}