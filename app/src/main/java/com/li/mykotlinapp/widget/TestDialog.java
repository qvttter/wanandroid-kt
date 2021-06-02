package com.li.mykotlinapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.li.mykotlinapp.R;

import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;


public class TestDialog extends Dialog {
    Button btnClose;
    public TestDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test);
        btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(v ->
                dismiss());
    }

    @Override
    public void show() {
        Window window = this.getWindow();
        window.setType(TYPE_SYSTEM_ALERT);
        super.show();
    }
}
