package com.jumo.ktor

sealed class ApiState<DATA, ERROR_DATA> {
  class Loading<DATA, ERROR_DATA> : ApiState<DATA, ERROR_DATA>()
  class Success<DATA, ERROR_DATA>(val data: DATA) : ApiState<DATA, ERROR_DATA>()
  class Error<DATA, ERROR_DATA>(
    val exception: Throwable,
    val errorData: ERROR_DATA? = null,
  ) : ApiState<DATA, ERROR_DATA>()
}