package com.example.chuanyue.testapp.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View

import kotlinx.android.synthetic.main.fragment_message.*
import com.example.chuanyue.testapp.R
import com.example.chuanyue.testapp.rxjava.RxBus
import com.example.chuanyue.testapp.rxjava.RxConst
import com.example.chuanyue.testapp.ui.viewmodel.FlutterViewModel


class MessageFragment: BaseFragment(), View.OnClickListener{
    private val model by lazy {
        activity.run {
            ViewModelProviders.of(activity!!).get(FlutterViewModel::class.java)
            //of()函数如果是this为参数，同一个activity内的不同fragment获取的是不同的ViewModel对象
        }
    }

    private val mDisposable by lazy {
        RxBus.listen(RxConst.EVENT_FLUTTER_ANDROID_RXJAVA_CHANNEL)
        .subscribe {
            message_text.text = it
//            Log.v(RxTAG,"Message接收")
        }
    }

    private val TAG = "MessageFragment"
    private val RxTAG = "RxJava"

    override val layoutId = R.layout.fragment_message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val a = activity!!.application
//        val i = activity!!.intent
//        val g = getArgsFromIntent(i)
//        FlutterMain.ensureInitializationComplete(a, g)
//        runArguments.bundlePath = FlutterMain.findAppBundlePath(activity!!.application)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        send_message.setOnClickListener(this)
        subscribeLiveDataMessage()
        //receiveRxJavaMessage()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.send_message -> {
                sendLiveDataMessage(message_edit.text.toString())
                //sendRxJavaMessage()
            }
        }
    }

    private fun subscribeLiveDataMessage(){
        model.flutterData.observe(this@MessageFragment, Observer {
                s -> receiveLiveDataMessage(s!!)
        })
    }

    private fun sendLiveDataMessage(string: String){
        model.flutterData.value = string
        //Log.v(TAG, "android已发送")
    }

    private fun receiveLiveDataMessage(string: String){
        message_text.text = string
        //Log.v(TAG, "android已接收")
    }

    private fun subcribeRxJavaMessage(){

    }

    private fun sendRxJavaMessage(){
        RxBus.post(RxConst.EVENT_ANDROID_FLUTTER_RXJAVA_CHANNEL)
//        Log.v(RxTAG,"Message发送")
    }

    private fun receiveRxJavaMessage(){
        addDisposable(mDisposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeDisposable(mDisposable)
    }
}

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

//        Flowable.create(
//            FlowableOnSubscribe<String> {
//                    emitter ->
//                        emitter.onNext(string)
//                        Log.v(TAG, string)
//            }, BackpressureStrategy.LATEST
//        ).subscribe(
//            object: Subscriber<String>{
//                private val mDisposable = Disposable()
//
//                override fun onComplete() {
//                    Log.v("TAG", "Complete")
//
//                }
//
//                override fun onError(t: Throwable?) {
//                    Log.v("TAG", t.toString())
//                }
//
//                override fun onNext(t: String?) {
//
//                }
//
//                override fun onSubscribe(s: Subscription?) {
//
//                }
//            }
//        )