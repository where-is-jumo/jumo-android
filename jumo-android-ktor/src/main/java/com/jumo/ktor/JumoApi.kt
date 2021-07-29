package com.jumo.ktor

import android.os.Build
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.DEFAULT_PORT
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.userAgent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

object JumoApi {
  lateinit var apiHost: String
  val client by lazy {
    createHttpClient(host = apiHost)
  }

  fun init(host: String) {
    apiHost = host
  }

  suspend inline fun <reified DATA, ERROR_DATA> request(
    path: String = "/",
  ): Flow<ApiState<DATA, ERROR_DATA>> = flow {
    runCatching {
      emit(ApiState.Loading<DATA, ERROR_DATA>())
      client.get<DATA>(path = path)
    }.onSuccess {
      emit(ApiState.Success<DATA, ERROR_DATA>(it))
    }.onFailure {
      emit(ApiState.Error<DATA, ERROR_DATA>(it))
    }
  }.flowOn(Dispatchers.IO)

  private fun createHttpClient(
    protocol: URLProtocol = URLProtocol.HTTPS,
    host: String,
    port: Int = DEFAULT_PORT,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
    agent: String = "Android/${Build.MANUFACTURER} - ${Build.MODEL}",
  ) = HttpClient {
    engine {

    }
    defaultRequest {
      url {
        this.protocol = protocol
        this.host = host
        this.port = port
        headers.forEach { (key, value) ->
          header(key, value)
        }
        contentType(contentType)
        userAgent(agent)
      }
    }
    install(JsonFeature) {
      serializer = KotlinxSerializer(Json {
        isLenient = true
        ignoreUnknownKeys = true
      })
    }
  }
}
