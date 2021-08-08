package com.jumo.ktx

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

@SuppressLint("StaticFieldLeak")
object JumoContext {
  private lateinit var instance: Application
  val application: Application
    get() = when {
      ::instance.isInitialized -> instance
      else -> throw Exception("The applicationContext is not initialized. Maybe JumoContext.init() is not declared in application class")
    }
  val appContext: Context
    get() = application.applicationContext

  fun init(appContext: Application) {
    instance = appContext
  }
}

internal inline val appContext: Context
  get() = JumoContext.appContext

@Suppress("UNCHECKED_CAST")
fun <T> Context.systemService(service: String): T = appContext.getSystemService(service) as T