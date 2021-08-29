package com.jumo.boilerplate.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import com.jumo.ktx.getNavHostFragment

open class JumoActivity<VDB : ViewDataBinding>(
  @LayoutRes private val layoutId: Int,
  @IdRes private val navControllerId: Int,
) : AppCompatActivity() {
  val binding: VDB by lazy {
    DataBindingUtil.setContentView<VDB>(this, layoutId)
  }

  val navController: NavController by lazy {
    getNavHostFragment(navControllerId).navController
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }
}