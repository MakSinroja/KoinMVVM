package com.example.koinmvvm.extensions

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun AppCompatActivity.setupTransparentActivity() {
    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun AppCompatActivity.setupFullScreenActivity() {
    this.requestWindowFeature(Window.FEATURE_NO_TITLE)
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun AppCompatActivity.setupStatusBarColor(attributeColorId: Int) {
    this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    this.window.statusBarColor = getColorFromAttribute(attributeColorId)
}

fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, bundle: Bundle? = null) {
    supportFragmentManager.inTransaction {
        bundle?.let {
            fragment.arguments = it
        }
        add(frameId, fragment)
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, bundle: Bundle? = null) {
    supportFragmentManager.inTransaction {
        bundle?.let {
            fragment.arguments = it
        }
        addToBackStack(fragment::class.java.simpleName)
        add(frameId, fragment)
    }
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction {
        remove(fragment)
    }
}

