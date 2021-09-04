package com.jumo.jumoexample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jumo.boilerplate.ui.JumoActivity
import com.jumo.jumoexample.databinding.ActivityMainBinding
import com.jumo.ktx.gone


class MainActivity : JumoActivity<ActivityMainBinding>(
  layoutId = R.layout.activity_main,
  navControllerId = R.id.nav_host_fragment,
  mainDestinations = setOf(
    R.id.navTestFragment,
    R.id.navExample2Fragment,
    R.id.navExample3Fragment,
  )
) {

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setBottomNavigation(binding.viewBottomNav)
  }

}
