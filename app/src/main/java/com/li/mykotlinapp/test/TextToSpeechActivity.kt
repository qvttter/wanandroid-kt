package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_text_to_speech.*
import java.util.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/6
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class TextToSpeechActivity : BaseActivity() {
    private lateinit var tts: TextToSpeech

    override fun getLayout(): Int {
        return R.layout.activity_text_to_speech
    }

    override fun initData() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            LogUtils.e("TextToSpeech:" + it)
            if (it != TextToSpeech.ERROR) {
                for(language in tts.availableLanguages){
                    LogUtils.e("availableLanguages:" + language.country)
                }

                var result = tts.setLanguage(Locale.JAPAN)
                if (result != TextToSpeech.LANG_AVAILABLE) {
                    shortToast("暂时不支持这种语言的朗读")
                }
            }
        })
        btn_text1.setOnClickListener {
            et_text.setText("こんにちは。")
        }
        btn_text2.setOnClickListener {
            et_text.setText("我が家へようこそ。")

        }
        btn_text3.setOnClickListener {
            et_text.setText("良い1日を。")
        }
        btn_text4.setOnClickListener {
            et_text.setText("ドアを閉じてください")
        }

        btn_play.setOnClickListener {
            var s = et_text.text.toString()
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null)
        }

    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, TextToSpeechActivity().javaClass)
            content.startActivity(intent)
        }
    }
}