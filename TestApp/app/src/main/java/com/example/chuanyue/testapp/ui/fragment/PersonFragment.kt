package com.example.chuanyue.testapp.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import java.util.ArrayList

import com.example.chuanyue.testapp.adapter.PersonAdapter
import com.example.chuanyue.testapp.R
import com.example.chuanyue.testapp.data.Person
import com.example.chuanyue.testapp.rxjava.RxBus
import com.example.chuanyue.testapp.rxjava.RxConst
import kotlinx.android.synthetic.main.fragment_person.*

class PersonFragment: LazyFragment(), View.OnClickListener{
    var mdata = ArrayList<Person>()
    val adapter by lazy { PersonAdapter(context!!,mdata) }
    val person = Person()
    private val mDisposable by lazy {
        RxBus.listen(RxConst.EVENT_ANDROID_FLUTTER_RXJAVA_CHANNEL)
            .subscribe {
                person_text.text = it
//            Log.v(RxTAG,"Message接收")
            }
    }

    override val layoutId = R.layout.fragment_person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mdata = person.initData(10)
        person.sortData(mdata)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
        recycler.layoutManager = LinearLayoutManager(context!!)
        add.setOnClickListener(this)
        add.setTextColor(ContextCompat.getColor(context!!, R.color.colorAccent))
        add.visibility = View.INVISIBLE
        del.setOnClickListener(this)
        addDisposable(mDisposable)
    }

    override fun onClick(view: View){
        when(view.id){
            R.id.add -> addItem()
            R.id.del -> delItem()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeDisposable(mDisposable)
    }

    private fun addItem(){
        adapter.addItem()
    }

    private fun delItem(){
        adapter.delItem()
        add.visibility = View.VISIBLE
    }

    override fun onInvisible() {}

    override fun onVisible() {}
}