package com.li.mykotlinapp.widget

import android.os.Bundle
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseDialog
import com.li.mykotlinapp.bean.ExcelItemBean
import com.li.mykotlinapp.util.DensityUtil
import kotlinx.android.synthetic.main.dialog_excel.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.widget
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/10/20
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class ExcelDialog :BaseDialog() {
    private var listener:OnCompleteListener?=null
    private var spinnerList: List<String> = listOf("男","女")
    private var currentSex = "男"

    override fun getLayout(): Int {
        return R.layout.dialog_excel
    }

    override fun initData() {
        spinner_sex.attachDataSource(spinnerList)
        spinner_sex.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            currentSex = spinnerList[position]
        }
        btn_ok.setOnClickListener {
            val name = et_name.text.toString()
            val age = et_age.text.toString()
            if (name.isEmpty())
                return@setOnClickListener

            if (age.isEmpty())
                return@setOnClickListener


            if (listener!=null){
                listener!!.onComplete(ExcelItemBean(name,age,currentSex))
                dismiss()
            }
        }
    }

    override fun onResume() {
        dialog!!.window!!.setLayout(DensityUtil.dip2px(activity,500f), DensityUtil.dip2px(activity,300f))
        super.onResume()
    }

    interface OnCompleteListener{
        fun onComplete(bean : ExcelItemBean)
    }

    fun setListener(onCompleteListener:OnCompleteListener){
        listener = onCompleteListener
    }

    companion object {

        fun newInstance(): ExcelDialog {
            var fragment = ExcelDialog()
            return fragment
        }
    }

}