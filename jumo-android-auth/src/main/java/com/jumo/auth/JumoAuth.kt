package com.jumo.auth

import android.content.Context
import com.kakao.sdk.common.KakaoSdk

object JumoAuth {
  fun initKakao(context: Context) {
    KakaoSdk.init(context, BuildConfig.KAKAO_API_KEY)
  }
}