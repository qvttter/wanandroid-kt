package com.li.mykotlinapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.li.mykotlinapp.test.opengl.MyGLSurfaceView;
import com.li.mykotlinapp.test.opengl.MyRender;


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/5/7
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
public class OpenGLActivity extends AppCompatActivity {

    private GLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlSurfaceView = new MyGLSurfaceView(this);
        setContentView(mGlSurfaceView);
        mGlSurfaceView.setRenderer(new MyRender(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGlSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGlSurfaceView.onPause();
    }
    public static void start(Context context){
        Intent intent= new Intent(context,OpenGLActivity.class);
        context.startActivity(intent);
    }


}
