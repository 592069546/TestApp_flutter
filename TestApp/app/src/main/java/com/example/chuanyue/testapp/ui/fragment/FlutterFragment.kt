package com.example.chuanyue.testapp.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

import io.flutter.facade.Flutter
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec

import com.example.chuanyue.testapp.R
import com.example.chuanyue.testapp.ui.viewmodel.FlutterViewModel

import kotlinx.android.synthetic.main.fragment_flutter.*

class FlutterFragment: LazyFragment(){
    private val CHANNEL = "message"
    private val TAG = "FlutterFragment"

    override val layoutId = R.layout.fragment_flutter
    private val flutterView by lazy { Flutter.createView(activity!!, lifecycle, "/") }
    private val messageChannel by lazy { BasicMessageChannel<String>(flutterView, CHANNEL, StringCodec.INSTANCE) }

    private val model by lazy {
        activity.run {
            ViewModelProviders.of(activity!!).get(FlutterViewModel::class.java) //?: throw Exception("Invild Activity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        showFlutter()
        subscribeMessage()
        touchArgument(view)    //令夫容器不拦截左右滑动事件

    }

    private fun showFlutter(){
        fragment_flutter_layout.addView(flutterView)
        messageChannel.setMessageHandler { s, reply ->
            receiveMessage(s)
            reply.reply(s)
        }
    }

    private fun subscribeMessage(){
        model.flutterData.observe(this@FlutterFragment, Observer { s ->  //一定要在主线程执行
            Log.v(TAG, s)
            sendMessage(s!!)
        })
    }

    private fun touchArgument(view: View){
//        val size = Point()
//        activity!!.windowManager.defaultDisplay.getSize(size)
//        val x = size.x / 2
//        val y = size.y / 2
//        fragment_flutter_layout.onInterceptTouchEvent(
//            MotionEvent.obtain(0, 0, MotionEvent.ACTION_MOVE, x.toFloat(), y.toFloat(), 0)
//        ).also {
//            Log.v("TAG",it.toString())
//        }
        val gestureDetector = GestureDetector(activity!!, listener)
        view.setOnTouchListener{ v, event ->
            gestureDetector.onTouchEvent(event)
        }
    }

    private val listener = object: GestureDetector.OnGestureListener{
        override fun onDown(e: MotionEvent?): Boolean { // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
            Log.v(TAG,MotionEvent.actionToString(e!!.action))
            return false
        }

        // 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            Log.v(TAG,"onFling")
            return false
        }

        // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
        override fun onLongPress(e: MotionEvent?) {
            Log.v(TAG,MotionEvent.actionToString(e!!.action))
        }

        // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            Log.v(TAG,"onScroll")
            return false
        }

        /*
        * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
        * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
        */
        override fun onShowPress(e: MotionEvent?) {
            Log.v(TAG,MotionEvent.actionToString(e!!.action))
        }

        // 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            Log.v(TAG,MotionEvent.actionToString(e!!.action))
            return false
        }
    }

    private fun sendMessage(string: String){ //向 flutter发送数据，接收MessageFragment数据
        messageChannel.send(string)
        Log.v(TAG, "android已发送")
    }

    private fun receiveMessage(string: String){
        model.flutterData.value = string
        Log.v(TAG, "android已接收")
    }

    override fun onInvisible() {}

    override fun onVisible() {}
}