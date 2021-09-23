package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text

class TestComposeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("Hello World")
        }
    }

//    override fun initData() {
//        setContent {
//            Text("Hello World")
//        }
//    }

    companion object {
            fun start(content: Context){
                val intent = Intent(content, TestComposeActivity().javaClass)
                content.startActivity(intent)
            }
        }
}