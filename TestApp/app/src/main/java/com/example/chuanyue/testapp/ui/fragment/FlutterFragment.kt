package com.example.chuanyue.testapp.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import io.flutter.facade.Flutter
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec
import io.flutter.view.FlutterView


import com.example.chuanyue.testapp.R
import com.example.chuanyue.testapp.ui.viewmodel.FlutterViewModel

import kotlinx.android.synthetic.main.fragment_flutter.*
import java.lang.Exception

class FlutterFragment: LazyFragment(){
    private val CHANNEL = "message"
    private val PING = "ping"
    private val TAG = "FlutterFragment"

    override val layoutId = R.layout.fragment_flutter
    lateinit var flutterView: FlutterView
    lateinit var messageChannel: BasicMessageChannel<String>

    private val model by lazy {
        activity.run {
            ViewModelProviders.of(activity!!).get(FlutterViewModel::class.java) //?: throw Exception("Invild Activity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        model = activity?.run {
//            ViewModelProviders.of(this@FlutterFragment).get(FlutterViewModel::class.java)
//        }?: throw Exception("Invalid Activity")
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        showFlutter()
        subscribeMessage()
    }

    private fun showFlutter(){
        flutterView =  Flutter.createView(activity!!, lifecycle, "/")
        fragment_flutter_layout.addView(flutterView)
        messageChannel = BasicMessageChannel<String>(flutterView, CHANNEL, StringCodec.INSTANCE)
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
//        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
        model.flutterData.value = string
        Log.v(TAG, "android已接收")
    }

    override fun onInvisible() {}

    override fun onVisible() {}
}