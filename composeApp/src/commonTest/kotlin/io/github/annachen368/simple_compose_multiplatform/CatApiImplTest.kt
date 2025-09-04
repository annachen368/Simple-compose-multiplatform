package io.github.annachen368.simple_compose_multiplatform

import io.github.annachen368.simple_compose_multiplatform.data.api.KtorCatApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CatApiImplTest {
    @Test
    fun `fetchCatResponse returns Success with parsed list`() = runTest {
        val sampleJson = """
            [
              {
                "id": 1,
                "name": "Test1",
                "gender": "Female",
                "age": 2,
                "adopted": false,
                "imageUrl": "https://placecats.com/200/200"
              },
              {
                "id": 2,
                "name": "Test2",
                "gender": "Male",
                "age": 3,
                "adopted": true,
                "imageUrl": "https://placecats.com/201/200"
              }
            ]
        """.trimIndent()

        val engine = MockEngine { request ->
            assertEquals(HttpMethod.Get, request.method)
            assertTrue(request.url.fullPath.endsWith("/cat.json"))
            respond(
                content = sampleJson,
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        val client = HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val api = KtorCatApiImpl(client)
        val result = api.fetchCatResponse()

        assertTrue(result.isSuccess)
        val cats = result.getOrThrow()
        assertEquals(2, cats.size)
        assertEquals("Test1", cats.first().name)
    }
}