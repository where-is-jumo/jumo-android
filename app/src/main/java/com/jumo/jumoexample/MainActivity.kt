package com.jumo.jumoexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jumo.ktx.gone


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

//    startActivity(Intent(this, OssLicensesMenuActivity::class.java))
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    val viewBottomNavigation = findViewById<BottomNavigationView>(R.id.viewBottomNav)
    viewBottomNavigation.setupWithNavController(navController)
  }

}
