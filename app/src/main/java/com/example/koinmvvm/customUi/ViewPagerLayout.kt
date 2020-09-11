package com.example.koinmvvm.customUi

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class ViewPagerLayout : ViewPager {

    constructor(context: Context) : super(context) {
        initialization()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initialization()
    }

    private fun initialization() {
        setPageTransformer(true, VerticalPageTransformer())
        overScrollMode = OVER_SCROLL_NEVER
    }

    fun swapXY(me: MotionEvent): MotionEvent {
        val mWidth = width
        val mHeight = height

        val newX = (me.y / mHeight) * mWidth
        val newY = (me.x / mWidth) * mHeight

        me.setLocation(newX, newY)

        return me
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val intercepted = super.onInterceptTouchEvent(ev?.let { swapXY(it) })
        ev?.let { swapXY(it) }
        return intercepted
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return super.onTouchEvent(ev?.let { swapXY(it) })
    }

    inner class VerticalPageTransformer : PageTransformer {

        val MIN_SCALE: Float = 0.75f

        override fun transformPage(page: View, position: Float) {

            if (position < -1) {
                page.alpha = 0.0f
            } else if (position <= 0) {
                page.alpha = 1.0f
                page.translationX = page.width * -position

                val yPosition = position * page.height
                page.translationY = yPosition
                page.scaleX = 1.0f
                page.scaleY = 1.0f
            } else if (position <= 1) {
                page.alpha = 1.0f
                page.translationX = (page.width * -position)

                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position))
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            } else {
                page.alpha = 0.0f
            }
        }
    }
}