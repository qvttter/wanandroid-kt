package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.li.mykotlinapp.R
import androidx.compose.foundation.lazy.items

import java.util.ArrayList

class TestComposeActivity : ComponentActivity() {
    private lateinit var list:MutableList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = ArrayList()
        for (i in 1..100){
            list.add("我是"+i)
        }
        setContent {
            Conversation(list)
        }
    }

    @Composable
    fun Conversation(texts: List<String>) {
        LazyColumn {
            items(texts) { string ->
                ComposeText(string)
            }
        }
    }

    @Preview
    @Composable
    fun PreviewText() {
        Conversation(list)
    }

    @Composable
    fun ComposeText(text: String) {
        Surface(
            shape = MaterialTheme.shapes.medium, // 使用 MaterialTheme 自带的形状
            elevation = 5.dp,
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.colorDarkGray))//.fillMaxWidth()相当于match_parent
        ){
            Row(modifier = Modifier.padding(all = 8.dp),verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                        contentDescription = "app",
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Column {
                    Text(text = "标题",color = colorResource(id = R.color.colorPrimary),fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    Text(text)
                }
            }
        }

    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, TestComposeActivity().javaClass)
            content.startActivity(intent)
        }
    }
}