package com.jumo.auth

sealed class AuthState {
  object Loading : AuthState()
  class Success(val id: String, val token: String, val provider: String) : AuthState()
  class Error(val exception: Throwable?) : AuthState()
}