package com.jumo.boilerplate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

open class JumoFragment<VDB : ViewDataBinding>(
  @LayoutRes private val layoutId: Int,
) : Fragment() {
  private lateinit var _binding: VDB
  val binding: VDB by lazy { _binding }

  val navController: NavController
    get() = findNavController()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {

    _binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
    return binding.root
  }

}