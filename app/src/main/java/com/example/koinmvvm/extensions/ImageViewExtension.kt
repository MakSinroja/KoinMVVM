package com.example.koinmvvm.extensions

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.koinmvvm.R

@SuppressLint("CheckResult")
fun AppCompatImageView.loadArticleImage(
    context: Context,
    url: Any?,
    placeHolder: Int = R.drawable.no_image_found_drawable,
    errorPlaceHolder: Int = R.drawable.ic_no_image_found
) {
    val requestOptions = RequestOptions()
    requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    requestOptions.placeholder(placeHolder)
    requestOptions.error(errorPlaceHolder)
    requestOptions.dontTransform()

    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        .into(this)
}