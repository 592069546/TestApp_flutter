package com.example.chuanyue.testapp.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class FlutterViewModel: ViewModel(){
    val flutterData by lazy { MutableLiveData<String>() }

    fun getMessage() = flutterData

    private fun setMessage(message: String){
        flutterData.value = message
    }
}