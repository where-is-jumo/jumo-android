package com.jumo.boilerplate.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jumo.ktx.getNavHostFragment
import com.jumo.ktx.gone

open class JumoActivity<VDB : ViewDataBinding>(
  @LayoutRes private val layoutId: Int,
  @IdRes private val navControllerId: Int,
  val mainDestinations: Set<Int>,
) : AppCompatActivity() {
  val binding: VDB by lazy {
    DataBindingUtil.setContentView<VDB>(this, layoutId)
  }

  val navController: NavController by lazy {
    getNavHostFragment(navControllerId).navController
  }

  private val appBarConfiguration by lazy {
    AppBarConfiguration(mainDestinations)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupActionBarWithNavController(navController, appBarConfiguration)

  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp(appBarConfiguration)
  }

  fun setBottomNavigation(viewBottomNavigation: BottomNavigationView) {
    viewBottomNavigation.setupWithNavController(navController)
    navController.addOnDestinationChangedListener { _, destination, _ ->
      viewBottomNavigation.gone(destination.id !in mainDestinations)
    }
  }
}