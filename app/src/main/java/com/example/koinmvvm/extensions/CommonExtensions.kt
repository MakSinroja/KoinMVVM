package com.example.koinmvvm.extensions

import android.content.Context
import android.content.Intent
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Context.getColorFromAttribute(
    @AttrRes attributeColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attributeColor, typedValue, resolveRefs)
    return typedValue.data
}

fun Context.shareContent(url: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, url)
    this.startActivity(Intent.createChooser(intent, "Share"))
}