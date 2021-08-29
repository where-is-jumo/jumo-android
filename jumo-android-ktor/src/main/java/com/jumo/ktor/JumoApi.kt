package com.jumo.ktor

import android.os.Build
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.DEFAULT_PORT
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.userAgent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

object JumoApi {
  private val defaultAgent = "Android/${Build.MANUFACTURER} - ${Build.MODEL}"
  fun create(
    protocol: URLProtocol = URLProtocol.HTTPS,
    host: String,
    port: Int = DEFAULT_PORT,
    headers: Map<String, String> = emptyMap(),
    agent: String = defaultAgent,
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
        userAgent(agent)
      }
    }
    install(JsonFeature) {
      serializer = KotlinxSerializer(Json {
        isLenient = true
        ignoreUnknownKeys = true
      })
    }
//    install(Logging) {
//      logger = Logger.SIMPLE
//      level = when {
//        BuildConfig.DEBUG -> LogLevel.ALL
//        else -> LogLevel.ALL
//      }
//    }
  }

  suspend inline fun <reified DATA, ERROR_DATA> HttpClient.request(
    path: String,
    method: HttpMethod = HttpMethod.Get,
    query: Map<String, Any> = emptyMap(),
    body: Map<String, Any> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
    appendHeader: Map<String, Any> = emptyMap(),
  ): Flow<ApiState<DATA, ERROR_DATA>> = flow {
    runCatching {
      emit(ApiState.Loading<DATA, ERROR_DATA>())

      this@request.request<DATA> {
        setHttpRequest(method, path, contentType, appendHeader)
        setBodyParam(method, query, body)
      }

    }.onSuccess {
      emit(ApiState.Success<DATA, ERROR_DATA>(it))
    }.onFailure {
      emit(ApiState.Error<DATA, ERROR_DATA>(it))
    }
  }.flowOn(Dispatchers.IO)

  fun HttpRequestBuilder.setHttpRequest(
    method: HttpMethod,
    path: String,
    contentType: ContentType,
    appendHeader: Map<String, Any>,
  ) {
    this.method = method
    url {
      encodedPath = path
      contentType(contentType)
      appendHeader.forEach { (key, value) ->
        header(key, value)
      }
    }
  }

  fun HttpRequestBuilder.setBodyParam(
    method: HttpMethod,
    query: Map<String, Any>,
    body: Map<String, Any>,
  ) {
    when (method) {
      HttpMethod.Get -> {
        query.forEach { (key, value) ->
          parameter(key, value)
        }
        body.forEach { (key, value) ->
          parameter(key, value)
        }
      }
      HttpMethod.Post,
      HttpMethod.Put,
      HttpMethod.Delete,
      -> when {
        body.isNotEmpty() -> this.body = body
      }
    }
  }
}
