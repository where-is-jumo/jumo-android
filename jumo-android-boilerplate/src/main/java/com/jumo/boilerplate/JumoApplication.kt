package com.jumo.boilerplate

import android.app.Application
import com.jumo.ktx.JumoContext

open class JumoApplication : Application() {
  init {
    JumoContext.init(this)
  }
}