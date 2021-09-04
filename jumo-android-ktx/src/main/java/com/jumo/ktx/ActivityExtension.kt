package com.jumo.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment

fun Intent.launchActivity(context: Context) = context.startActivity(this)

inline fun <reified A : Activity> ActivityResultLauncher<Intent>.launchActivity(
  context: Context?,
  extra: Bundle = bundleOf(),
) = this.launch(Intent(context, A::class.java).apply {
  putExtras(extra)
})

fun FragmentActivity.getNavHostFragment(@IdRes id: Int): NavHostFragment {
  return supportFragmentManager.findFragmentById(id) as NavHostFragment
}
