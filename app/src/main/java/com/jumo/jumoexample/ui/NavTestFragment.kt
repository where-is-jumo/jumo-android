package com.jumo.jumoexample.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jumo.boilerplate.ui.JumoFragment
import com.jumo.jumoexample.R
import com.jumo.jumoexample.databinding.FragmentNavTestBinding

class NavTestFragment : JumoFragment<FragmentNavTestBinding>(
  R.layout.fragment_nav_test
) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.viewHelloWrold.setOnClickListener {
      val navAction = NavTestFragmentDirections.moveToNavTest2("Jumo!!")
      findNavController().navigate(navAction)
    }
  }
}