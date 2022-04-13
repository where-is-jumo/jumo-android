package com.jumo.auth

import android.content.Context
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalCoroutinesApi::class)
object KakaoAuth {
  private const val provider = "kakao"

  fun init(context: Context) {
    KakaoSdk.init(context, BuildConfig.KAKAO_API_KEY)
  }

  fun login(context: Context): Flow<AuthState> = callbackFlow {
    channel.send(AuthState.Loading)
    val oAuthToken = AuthApiClient.instance.tokenManagerProvider.manager.getToken()
    when {
      oAuthToken != null -> accessTokenInfo(context, oAuthToken, needLogin = true)
      else -> launchKakao(context)
    }
    awaitClose()
  }

  private fun ProducerScope<AuthState>.accessTokenInfo(
    context: Context, oAuthToken: OAuthToken, needLogin: Boolean = false,
  ) {
    UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
      when {
        //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
        error == null && tokenInfo != null -> {
          channel.trySendBlocking(
            AuthState.Success(tokenInfo.id.toString(), oAuthToken.accessToken, provider)
          )
          channel.close()
        }
        needLogin && error is KakaoSdkError && error.isInvalidTokenError() -> {
          AuthApiClient.instance.tokenManagerProvider.manager.clear()
          launchKakao(context)
        }
        else -> {
          AuthApiClient.instance.tokenManagerProvider.manager.clear()
          channel.trySendBlocking(AuthState.Error(error))
          channel.close()
        }
      }
    }
  }

  private fun ProducerScope<AuthState>.launchKakao(context: Context) {
    when {
      UserApiClient.instance.isKakaoTalkLoginAvailable(context) -> loginByKakaoTalk(context)
      else -> loginByKakaoAccount(context)
    }
  }

  private fun ProducerScope<AuthState>.loginByKakaoTalk(context: Context) {
    UserApiClient.instance.loginWithKakaoTalk(context) { token: OAuthToken?, error ->
      when {
        token != null -> accessTokenInfo(context, token, needLogin = true)
        error is ClientError && error.reason != ClientErrorCause.Cancelled -> {
          loginByKakaoAccount(context)
        }
        else -> {
          channel.trySendBlocking(AuthState.Error(error))
          channel.close()
        }
      }
    }
  }

  private fun ProducerScope<AuthState>.loginByKakaoAccount(context: Context) {
    UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
      when {
        token != null -> accessTokenInfo(context, token, needLogin = true)
        else -> {
          channel.trySendBlocking(AuthState.Error(error))
          channel.close()
        }
      }
    }
  }
}