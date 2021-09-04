package com.jumo.jumoexample.ui

import android.os.Bundle
import android.view.View
import com.jumo.boilerplate.ui.JumoFragment
import com.jumo.jumoexample.R
import com.jumo.jumoexample.databinding.FragmentExample2Binding

class Example2Fragment : JumoFragment<FragmentExample2Binding>(
  R.layout.fragment_example2
) {
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }
}