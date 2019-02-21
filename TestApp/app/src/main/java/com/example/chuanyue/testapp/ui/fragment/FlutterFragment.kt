package com.example.chuanyue.testapp.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View

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