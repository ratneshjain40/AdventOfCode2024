package com.github.ratneshjain40.utils

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.github.cdimascio.dotenv.dotenv

private val dotenv = dotenv()

object InputFetcher {
    private val session = dotenv["SESSION"]

    private val client = HttpClient(CIO) {
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }
    }


    private fun makeUrl(day: Int): String {
        return "https://adventofcode.com/2024/day/$day/input"
    }

    suspend fun fetch(day: Int): String {
        val url = makeUrl(day)
        println("Fetching $url")
        val response = client.request(url) {
            method = HttpMethod.Get
            headers {
                append("cookie", "session=$session")
            }
        }
        return response.bodyAsText()
    }
}
