package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.base.BaseBindingActivity
import com.li.mykotlinapp.databinding.ActivityTestWqBinding

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/9/21
 *@Copyright:(C)2023 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class TestWQActivity : BaseBindingActivity<ActivityTestWqBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityTestWqBinding
        get() = ActivityTestWqBinding::inflate

    override fun initData() {
        binding.rgMode.setOnCheckedChangeListener { radioGroup, i ->
            if (binding.rbFreeFill.isChecked){
                binding.viewCircleDraw.currentMode = CircleDrawView.MODE_FREE_FILL
            }else if (binding.rbRectFill.isChecked){
                binding.viewCircleDraw.currentMode = CircleDrawView.MODE_RECT_FILL
            }
            else if (binding.rbEraser.isChecked){
                binding.viewCircleDraw.currentMode = CircleDrawView.MODE_ERASER
            }
        }

        //矩形模式/交错模式
        binding.rgCrossMode.setOnCheckedChangeListener { radioGroup, i ->
            if (binding.rbRectMode.isChecked){
                binding.viewCircleDraw.isCrossMode = false
            }else if (binding.rbCrossMode.isChecked){
                binding.viewCircleDraw.isCrossMode = true
            }
        }

        binding.btnClear.setOnClickListener {
            binding.viewCircleDraw.clear()
        }

        binding.btnUndo.setOnClickListener {
            binding.viewCircleDraw.undo()
        }
    }

    companion object {
            fun start(content: Context){
                val intent = Intent(content, TestWQActivity().javaClass)
                content.startActivity(intent)
            }
        }
}