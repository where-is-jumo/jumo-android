package com.jumo.jumoexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.jumo.jumoexample.R
import com.jumo.jumoexample.listItemExample
import com.jumo.ktx.toast

class NavTest2Fragment : Fragment() {
  private val arg: NavTest2FragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    return inflater.inflate(R.layout.fragment_nav_test2, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val epoxy = view.findViewById<EpoxyRecyclerView>(R.id.viewEpoxyList)
    epoxy.withModels {
      val list = arrayOf(
        "고양이1" to "https://cdn2.thecatapi.com/images/YQKJJcqNZ.jpg",
        "고양이2" to "https://cdn2.thecatapi.com/images/p46ys1bGF.jpg",
        "고양이3" to "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
      )
      list.forEachIndexed { index, (title, url) ->
        listItemExample {
          id("item")
          title(title)
          imageUrl(url)
          onClickItem { model, parentView, clickedView, position ->
            toast("clicked $position item\n title is ${model.title()}")
          }
        }
      }
    }
  }
}