package com.example.chuanyue.testapp.rxjava

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber

class RxBus{
    companion object {
        val instance = RxBus()
        private val RxTAG = "RxJava"
        private val receive = "RxJava receive"
        private val send = "RxJava send"
//        class BaseRxEvent(private val mEvent: String): IRxEvent{
//            override fun check(event: String) = event.isNotEmpty() && event == mEvent
//        }

        fun <T: IRxEvent>listen(eventClazz: Class<T> ,vararg events: String): Flowable<T>{
            return instance.mProcessor    //继承Flowable
                .onBackpressureBuffer()   //缓存所有当前无法消费的数据，直到 Observer 可以处理为止
                .ofType(eventClazz)   //过滤不符合eventClazz类型的信息
                .filter{ t ->    //通过一定逻辑来过滤被观察者发送的事件，如果返回 true 则会发送事件，否则不会发送
                    if (events.isEmpty()) return@filter true
                    for (event in events){
                        if(t.check(event)) return@filter true
                    }
                    //Log.v(RxTAG, receive)
                    false
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

        fun listen(vararg event: String): Flowable<String>{
            return listen(BaseRxEvent::class.java, *event)//.also { Log.v(RxTAG, event[0]) }
                .map{
                    it.getEvent()   //把BaseRxEvent转换为String
                }
        }

        fun post(event: String){
            if(TextUtils.isEmpty(event)) return
            post(BaseRxEvent(event))
        }

        fun post(event: IRxEvent){
            synchronized(instance.lock){
                instance.mHandler.post {
                    instance.mProcessor.onNext(event).also {
                        //Log.v(RxTAG,send)
                    }
                }
            }
        }

//        fun post(event: String): Flowable<String>{
//            return Flowable.create({
//                    emitter -> emitter.onNext(event)
//                },BackpressureStrategy.LATEST
//            )
//        }

        class BaseRxEvent(private var mEvent: String): IRxEvent{
            override fun check(event: String): Boolean {
                //Log.v(RxTAG,"$mEvent $event")
                return mEvent == event
            }

            fun getEvent() = mEvent
        }


    }



    private val publishProcessor: PublishProcessor<IRxEvent> = PublishProcessor.create()
    private val mProcessor by lazy { publishProcessor.toSerialized() }
    private val mHandler by lazy { Handler(Looper.getMainLooper()) }
    private val lock by lazy { Object() }

    private constructor()

    interface IRxEvent{
        fun check(event: String): Boolean
    }
}