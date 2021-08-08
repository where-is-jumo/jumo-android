package com.jumo.jumoexample

import android.app.Application
import android.content.Context
import com.jumo.ktx.JumoContext

//TODO create JumoApplication in jumo-android-base module
class JumoSampleApp : Application() {
  init {
    instance = this
    JumoContext.init(this)
  }

  companion object {
    private lateinit var instance: JumoSampleApp
    val appContext: Context
      get() = instance.applicationContext
  }
}