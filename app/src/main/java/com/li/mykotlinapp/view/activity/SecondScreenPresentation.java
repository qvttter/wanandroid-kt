package com.li.mykotlinapp.view.activity;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

import com.li.mykotlinapp.R;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/12/14
 *@Copyright:(C)2021 . All rights reserved.
 *************************************************************************/
public class SecondScreenPresentation extends Presentation {
    private TextView tvSecond;

    /** Context 可以为Activity Application Service **/
    public SecondScreenPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_presentation);
        tvSecond = findViewById(R.id.tv_second_screen_text);
        init();
    }

    private void init(){
//        initView();
        initEvent();
    }

    private void initEvent() {

    }

    public void changeSecondScreenText(String s){
        tvSecond.setText(s+"");
    }
}
