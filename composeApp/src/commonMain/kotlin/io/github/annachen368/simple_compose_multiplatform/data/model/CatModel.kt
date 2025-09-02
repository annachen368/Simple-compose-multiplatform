package io.github.annachen368.simple_compose_multiplatform.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Cat(
    val id: Int,
    val name: String,
    val gender: String,
    val age: Int,
    val adopted: Boolean,
    val imageUrl: String?
)
