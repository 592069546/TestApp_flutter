package com.example.chuanyue.testapp.ui.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent

class FlutterFragmentLayout: ConstraintLayout{
    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyle:Int): super(context, attrs, defStyle)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val size = Point()
        (context as Activity).windowManager.defaultDisplay.getSize(size)
        val x = size.x / 2
        val y = size.y / 2
        val action = MotionEvent.obtain(0, 0, MotionEvent.ACTION_MOVE, x.toFloat(), y.toFloat(), 0)
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}