package com.jumo.ktx

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

@SuppressLint("StaticFieldLeak")
object JumoContext {
  private lateinit var instance: Context
  val appContext: Context
    get() = when {
      ::instance.isInitialized -> instance.applicationContext
      else -> throw Exception("The applicationContext is not initialized. Maybe JumoContext.init() is not declared in application class")
    }

  fun init(appContext: Context) {
    instance = appContext
  }
}

internal inline val appContext: Context
  get() = JumoContext.appContext