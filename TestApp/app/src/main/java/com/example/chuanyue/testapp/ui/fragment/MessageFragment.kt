package com.example.chuanyue.testapp.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View

import kotlinx.android.synthetic.main.fragment_message.*
import com.example.chuanyue.testapp.R
import com.example.chuanyue.testapp.ui.viewmodel.FlutterViewModel

class MessageFragment: BaseFragment(), View.OnClickListener{
    private val model by lazy {
        activity.run {
            ViewModelProviders.of(activity!!).get(FlutterViewModel::class.java) //?: throw Exception("Invild Activity")
            //of()函数如果是this为参数，同一个activity内的不同fragment获取的是不同的ViewModel对象
        }
    }
    private val TAG = "MessageFragment"

    override val layoutId = R.layout.fragment_message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val a = activity!!.application
//        val i = activity!!.intent
//        val g = getArgsFromIntent(i)
//        //FlutterMain.ensureInitializationComplete(a, g)
//        //runArguments.bundlePath = FlutterMain.findAppBundlePath(activity!!.application)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        send_message.setOnClickListener(this)
        subscribeMessage()
    }

    private fun sendMessage(string: String){
        model.flutterData.value = string
        Log.v(TAG, "android已发送")
    }

    private fun receiveMessage(string: String){
        message_text.text = string
        Log.v(TAG, "android已接收")
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.send_message -> {
                sendMessage(message_edit.text.toString())
            }
        }
    }

    private fun subscribeMessage(){
        model.flutterData.observe(this@MessageFragment, Observer { s -> receiveMessage(s!!) })
    }

//
//    private fun getArgsFromIntent(intent: Intent): Array<String>{
//        val args = ArrayList<String>()
//        if(intent.getBooleanExtra("trace-startup", false)){
//            args.add("--trace-startup")
//        }
//        if (intent.getBooleanExtra("start-paused", false)) {
//            args.add("--start-paused")
//        }
//        if (intent.getBooleanExtra("enable-dart-profiling", false)) {
//            args.add("--enable-dart-profiling")
//        }
//        return args.toTypedArray()
//    }
}