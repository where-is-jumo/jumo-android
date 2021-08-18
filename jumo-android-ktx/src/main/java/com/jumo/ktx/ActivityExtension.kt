package com.jumo.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.core.os.bundleOf

fun Intent.launchActivity(context: Context) = context.startActivity(this)

inline fun <reified A : Activity> ActivityResultLauncher<Intent>.launchActivity(
  context: Context?,
  extra: Bundle = bundleOf(),
) = this.launch(Intent(context, A::class.java).apply {
  putExtras(extra)
})
