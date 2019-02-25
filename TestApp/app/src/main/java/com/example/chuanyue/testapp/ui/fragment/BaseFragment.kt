package com.example.chuanyue.testapp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment: Fragment(){
    companion object {
        val TAG = "BaseFragment"
    }

    private var mCompositeDisposable: CompositeDisposable ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Log.v(TAG,"onCreateView")
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    protected abstract val layoutId: Int

    protected abstract fun initView(view: View, savedInstanceState: Bundle?)

    fun addDisposable(disposable: Disposable){
        if(mCompositeDisposable == null){
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }

    fun removeDisposable(disposable: Disposable) {
        mCompositeDisposable?.remove(disposable)
    }

    fun clearAllDisposable() {
        mCompositeDisposable?.clear()
    }
}