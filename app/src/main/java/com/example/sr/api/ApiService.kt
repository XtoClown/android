package com.example.sr.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiService {

    val client = HttpClient(Android){
        install(ContentNegotiation){
            json( Json{
                prettyPrint = true; isLenient = true; ignoreUnknownKeys = true;
            })
        }
    }

    val apiToken = "cur_live_HKP1YHK3eZZTAQ9rVwW0blZpaEDYgVUgcXWsEuQX"
    val apiUrl = "https://api.currencyapi.com/v3/latest"

    suspend fun convert(from: String, to: String): CurrencyJSON {
        return client.get(apiUrl){
            url{
                parameter("apikey", apiToken)
                parameter("base_currency", from)
                parameter("currencies", to)
            }
        }.body()
    }

    fun close(){
        client.close()
    }

}