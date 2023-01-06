package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.TestButtonListAdapter
import com.li.mykotlinapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test_recycler.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/11/8
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class TestRecyclerActivity :BaseActivity(){
    private lateinit var list:MutableList<String>
    private lateinit var adapter: TestButtonListAdapter

    override fun getLayout(): Int {
        return R.layout.activity_test_recycler
    }

    override fun initData() {
        list = ArrayList()
        for (i in 1 ..20){
            list.add("test$i")
        }
        tv_info.text = "list.size:"+list.size
        adapter = TestButtonListAdapter(list)
        adapter.data = list
        rcv_test.adapter = adapter
        var linearLayoutManager =LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rcv_test.layoutManager = linearLayoutManager
        rcv_test.isNestedScrollingEnabled = false
    }

    companion object {
            fun start(content: Context){
                val intent = Intent(content, TestRecyclerActivity().javaClass)
                content.startActivity(intent)
            }
        }

}