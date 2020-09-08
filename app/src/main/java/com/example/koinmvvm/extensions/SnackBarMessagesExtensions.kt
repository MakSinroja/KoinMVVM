package com.example.koinmvvm.extensions

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.koinmvvm.R
import com.google.android.material.snackbar.Snackbar

fun String.showFailureSnackBarView(view: View) {
    val snackBarView = Snackbar.make(view, this, Snackbar.LENGTH_LONG)
    val sbView = snackBarView.view
    sbView.setBackgroundResource(R.drawable.rounded_corner_rectangle_failure_drawable)
    snackBarView.show()
}

fun String.showSuccessSnackBarView(view: View) {
    val snackBarView = Snackbar.make(view, this, Snackbar.LENGTH_LONG)
    val sbView = snackBarView.view
    sbView.setBackgroundResource(R.drawable.rounded_corner_rectangle_success_drawable)
    snackBarView.show()
}

@SuppressLint("ResourceAsColor")
fun String.showWarningSnackBarView(view: View) {
    val snackBarView = Snackbar.make(view, this, Snackbar.LENGTH_LONG)
    val sbView = snackBarView.view
    sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        .setTextColor(ContextCompat.getColor(view.context, android.R.color.black))
    sbView.setBackgroundResource(R.drawable.rounded_corner_rectangle_warning_drawable)
    snackBarView.show()
}