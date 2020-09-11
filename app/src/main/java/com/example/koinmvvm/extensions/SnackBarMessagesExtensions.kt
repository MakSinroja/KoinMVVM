package com.example.koinmvvm.extensions

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.koinmvvm.R
import com.google.android.material.snackbar.Snackbar

fun String.showFailureSnackBarView(view: View) {
    showSnackBarView(view, this, R.drawable.rounded_corner_rectangle_failure_drawable)
}

fun String.showSuccessSnackBarView(view: View) {
    showSnackBarView(view, this, R.drawable.rounded_corner_rectangle_success_drawable)
}

@SuppressLint("ResourceAsColor")
fun String.showWarningSnackBarView(view: View) {
    showSnackBarView(view, this, R.drawable.rounded_corner_rectangle_warning_drawable)
}

fun showSnackBarView(view: View, text: String, drawable: Int) {
    val snackBarView = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
    val sbView = snackBarView.view
    sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        .setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
    sbView.setBackgroundResource(R.drawable.rounded_corner_rectangle_warning_drawable)
    snackBarView.show()
}