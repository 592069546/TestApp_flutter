package com.example.chuanyue.testapp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.chuanyue.testapp.ui.fragment.FlutterFragment
import com.example.chuanyue.testapp.ui.fragment.MessageFragment
import com.example.chuanyue.testapp.ui.fragment.PersonFragment

class MainViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

    private val mTitle = arrayOf("Flutter", "Person")

    val mFlutterFragment by lazy { FlutterFragment() }
    val mPersonFragment by lazy { PersonFragment() }
//    val mMessageFragment by lazy { MessageFragment() }


    override fun getItem(position: Int): Fragment = when (position) {
        0 -> mFlutterFragment
        1 -> mPersonFragment
//        2 -> mMessageFragment
        else -> mFlutterFragment //when作为表达式else为必须
    }

    override fun getCount(): Int = mTitle.size

    override fun getPageTitle(position: Int): CharSequence? = mTitle[position]
}