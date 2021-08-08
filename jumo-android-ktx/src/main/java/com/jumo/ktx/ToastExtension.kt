package com.jumo.ktx

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

internal fun Context.createToast(text: CharSequence, duration: Int): Toast =
  Toast.makeText(this, text, duration)

internal fun Context.createToast(@StringRes textResId: Int, duration: Int): Toast =
  Toast.makeText(this, textResId, duration)

fun toast(text: String): Toast = appContext.toast(text)
fun Context.toast(text: String): Toast =
  createToast(text, Toast.LENGTH_SHORT).apply {
    show()
  }

fun toast(@StringRes textResId: Int): Toast = appContext.toast(textResId)
fun Context.toast(@StringRes textResId: Int): Toast =
  createToast(textResId, Toast.LENGTH_SHORT).apply {
    show()
  }

fun toastLong(text: String): Toast = appContext.toastLong(text)
fun Context.toastLong(text: String): Toast =
  createToast(text, Toast.LENGTH_LONG).apply {
    show()
  }

fun toastLong(@StringRes textResId: Int): Toast = appContext.toastLong(textResId)
fun Context.toastLong(@StringRes textResId: Int): Toast =
  createToast(textResId, Toast.LENGTH_LONG).apply {
    show()
  }