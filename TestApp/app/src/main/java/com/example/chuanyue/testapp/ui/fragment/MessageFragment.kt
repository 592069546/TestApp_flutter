package com.example.chuanyue.testapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec
import io.flutter.view.FlutterMain
import io.flutter.view.FlutterRunArguments

import kotlinx.android.synthetic.main.fragment_message.*
import com.example.chuanyue.testapp.R

class MessageFragment: BaseFragment(), View.OnClickListener{
    private val CHANNEL = "message"
    private val PING = "ping"
    private var messageChannel = BasicMessageChannel<String>(flutter_view, CHANNEL, StringCodec.INSTANCE)
    val runArguments = FlutterRunArguments()

    override val layoutId = R.layout.fragment_message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val a = activity!!.application
        val i = activity!!.intent
        val g = getArgsFromIntent(i)
        //FlutterMain.ensureInitializationComplete(a, g)
        //runArguments.bundlePath = FlutterMain.findAppBundlePath(activity!!.application)
        messageChannel.setMessageHandler { s, reply ->
            receiveMessage(s)
            reply.reply(s)
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        send_message.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.send_message -> {
                sendMessage()
            }
        }
    }

    private fun sendMessage(){
        messageChannel.send(PING)
    }

    private fun receiveMessage(string: String){
        message_text.text = string
    }

    private fun getArgsFromIntent(intent: Intent): Array<String>{
        val args = ArrayList<String>()
        if(intent.getBooleanExtra("trace-startup", false)){
            args.add("--trace-startup")
        }
        if (intent.getBooleanExtra("start-paused", false)) {
            args.add("--start-paused")
        }
        if (intent.getBooleanExtra("enable-dart-profiling", false)) {
            args.add("--enable-dart-profiling")
        }
        return args.toTypedArray()
    }
}