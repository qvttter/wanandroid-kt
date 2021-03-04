package com.li.mykotlinapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.li.mykotlinapp.R;
import com.li.mykotlinapp.test.OMAPITestFragment;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/8/14 
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved. 
 *************************************************************************/
public class OmapiActivity extends AppCompatActivity {
    private static final String TAG_OMAPI_TEST_VIEW = "tagomapitestfragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omapi);

        OMAPITestFragment omapiTestFragment = OMAPITestFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, omapiTestFragment, TAG_OMAPI_TEST_VIEW)
                .addToBackStack(null).commit();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, OmapiActivity.class);
        context.startActivity(intent);
    }
}
