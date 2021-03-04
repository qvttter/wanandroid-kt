package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.easyway.ipu.base.BaseVMActivity
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.CommonStringAdapter
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.bean.db.UserDbBean
import com.li.mykotlinapp.biz.db.DbBiz
import com.li.mykotlinapp.biz.db.ObjectBox
import com.li.mykotlinapp.util.RxUtil
import com.li.mykotlinapp.view.vm.ObjectVM
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_object_box.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/7/9
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class ObjectActivity : BaseVMActivity<ObjectVM>() {
    private lateinit var adapter: CommonStringAdapter
    private lateinit var list: MutableList<UserDbBean>
    override fun getLayout(): Int {
        return R.layout.activity_object_box
    }

    override fun initData() {
        list = ArrayList()
        adapter = CommonStringAdapter(list)
        rcv_common.layoutManager = LinearLayoutManager(mContext)
        rcv_common.addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
        rcv_common.adapter = adapter

        adapter.setOnItemClickListener { adapter, view, position ->
            var bean = list[position]
            MaterialDialog(this).show {
                input(prefill = bean.name) { dialog, text ->
                    bean.name = text.toString()
                    mViewModel.change(bean)
                }
                positiveButton(R.string.str_confirm)
            }
        }

        adapter.setOnItemLongClickListener { adapter, view, position ->
            mViewModel.delete(list[position])
            true
        }

        mViewModel.apply {
            isLoading.observe(this@ObjectActivity, Observer {
                if (it) showLoading("") else hideLoading()
            })

            message.observe(this@ObjectActivity, Observer {
                if (it.isNotEmpty()) shortToast(it)
            })

            users.observe(this@ObjectActivity, Observer {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            })
        }

        btn_add.setOnClickListener {
            mViewModel.add(UserDbBean(name = "张三",age=30, number = System.currentTimeMillis()))
        }

        btn_delete_all.setOnClickListener {
            mViewModel.deleteAll()
        }

        btn_search_all.setOnClickListener {
            mViewModel.searchAll()
        }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, ObjectActivity().javaClass)
            content.startActivity(intent)
        }
    }

}