package io.github.annachen368.simple_compose_multiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform