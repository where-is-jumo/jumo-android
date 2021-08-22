package com.jumo.jumoexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.jumo.jumoexample.R

class NavTestFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    return inflater.inflate(R.layout.fragment_nav_test, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val tv: TextView = view.findViewById(R.id.viewHelloWrold)
    tv.setOnClickListener {
      val navAction = NavTestFragmentDirections.moveToNavTest2("Jumo!!")
      findNavController().navigate(navAction)
    }
  }
}