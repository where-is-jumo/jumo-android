package com.jumo.boilerplate

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

object ImageViewBindingAdapters {
  @BindingAdapter("glide_imageUrl", "glide_placeholder", requireAll = false)
  @JvmStatic
  fun ImageView.loadImage(url: String?, placeholder: Drawable?) {
    Glide.with(this.context)
      .load(url)
      .placeholder(placeholder)
      .signature(ObjectKey("${url}_${placeholder}"))
      .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
      .apply(RequestOptions().fitCenter())
      .into(this)
  }
}