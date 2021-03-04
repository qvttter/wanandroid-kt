package com.li.mykotlinapp.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import org.simalliance.openmobileapi.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/8/14 
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved. 
 *************************************************************************/
public class hellosmartcardActivity extends AppCompatActivity implements SEService.CallBack {
    final String LOG_TAG = "HelloSmartcard";

    private SEService seService;

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        button = new Button(this);
        button.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        button.setText("Click Me");
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Log.i(LOG_TAG, "Retrieve available readers...");
                    Reader[] readers = seService.getReaders();
                    if (readers.length < 1)
                        return;

                    Log.i(LOG_TAG, "Create Session from the first reader...");
                    Session session = readers[0].openSession();

                    Log.i(LOG_TAG, "Create logical channel within the session...");
                    Channel channel = session.openLogicalChannel(new byte[]{
                            (byte) 0xD2, 0x76, 0x00, 0x01, 0x18, 0x00, 0x02,
                            (byte) 0xFF, 0x49, 0x50, 0x25, (byte) 0x89,
                            (byte) 0xC0, 0x01, (byte) 0x9B, 0x01});

                    Log.i(LOG_TAG, "Send HelloWorld APDU command");
                    byte[] respApdu = channel.transmit(new byte[]{
                            (byte) 0x90, 0x10, 0x00, 0x00, 0x00});

                    channel.close();

                    // Parse response APDU and show text but remove SW1 SW2 first
                    byte[] helloStr = new byte[respApdu.length - 2];
                    System.arraycopy(respApdu, 0, helloStr, 0, respApdu.length - 2);
                    Toast.makeText(hellosmartcardActivity.this, new String(helloStr), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Error occured:", e);
                    return;
                }
            }
        });

        layout.addView(button);
        setContentView(layout);


        try {
            Log.i(LOG_TAG, "creating SEService object");
            seService = new SEService(this, this);
        } catch (SecurityException e) {
            Log.e(LOG_TAG, "Binding not allowed, uses-permission org.simalliance.openmobileapi.SMARTCARD?");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e.getMessage());
        }
    }


    @Override
    protected void onDestroy() {
        if (seService != null && seService.isConnected()) {
            seService.shutdown();
        }
        super.onDestroy();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, hellosmartcardActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void serviceConnected(SEService seService) {
        Log.i(LOG_TAG, "seviceConnected()");
        button.setEnabled(true);
    }
}
