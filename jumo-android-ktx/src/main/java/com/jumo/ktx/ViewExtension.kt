package com.jumo.ktx

import android.view.View

fun View.visible() {
  this.visibility = View.VISIBLE
}

fun View.gone(gone: Boolean = true) {
  this.visibility = when {
    gone -> View.GONE
    else -> View.VISIBLE
  }
}

fun View.invisible(invisible: Boolean = true) {
  this.visibility = when {
    invisible -> View.INVISIBLE
    else -> View.VISIBLE
  }
}