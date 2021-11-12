package com.li.mykotlinapp.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.li.mykotlinapp.R;
import com.li.mykotlinapp.util.CommonUtils;


/************************************************************************
 *@Project: InputView
 *@Package_Name: com.bzt.inputview.inputview
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/2/10
 *@Copyright:(C)2018 苏州百智通信息技术有限公司. All rights reserved.
 *************************************************************************/
public class InputView extends LinearLayout {
    private static final int TEXT_SIZE = 16;
    private static final int BTN_SIZE = 16;

    private Context mContext;
    private OnInputCallBack callBack;
    private TypedArray typedArray;
    /*在收起键盘，点击红白区域时，是否收起底部view。默认开启*/
    private boolean isInputViewAutoHide = true;

    /*---------------属性---------------*/
    private int defTextColor = Color.rgb(51, 51, 51);
    //    private int defHintColor = Color.rgb(51, 51, 51);
    private int defBtnColor = Color.rgb(51, 51, 51);
    private int defBtnDefaultColor = Color.rgb(137, 137, 137);
    private int defInputBackgroundColor = Color.rgb(255, 255, 255);
    private String hint;

    private int currentKeyboardH;

    //    private TextView tvContent;
//    private TextView tvSend;
    /*---------------输入部分---------------*/
    private RelativeLayout rlComment;
    private View vTransparent;
    private EditText etContent;
    private TextView tvSendPop;
    private int textColor;
    private int btnColor;
    private int btnDefaultColor;
    private int inputBgColor;
    private int textSize;
    private int btnSize;
    private View bodyLayout;


    public InputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.input);

        LayoutInflater.from(context).inflate(R.layout.input_view, this);
        initView();
        initEvent();
    }

    private void initView() {
        bodyLayout = findViewById(R.id.rl_bodyLayout);
//        tvContent = (TextView) findViewById(R.id.tv_content);
//        tvSend = (TextView) findViewById(R.id.tv_send);
        /*---------------输入部分---------------*/
        rlComment = (RelativeLayout) findViewById(R.id.rl_comment);
        vTransparent = findViewById(R.id.v_transparent);
        etContent = (EditText) findViewById(R.id.et_content);
        tvSendPop = (TextView) findViewById(R.id.tv_send_pop);

        hint = typedArray.getString(R.styleable.input_hint);
        textColor = typedArray.getColor(R.styleable.input_textColor, defTextColor);
        btnColor = typedArray.getColor(R.styleable.input_btnColor, defBtnColor);
        btnDefaultColor = typedArray.getColor(R.styleable.input_btnDefaultColor, defBtnDefaultColor);
        inputBgColor = typedArray.getColor(R.styleable.input_inputBackgroundColor, defInputBackgroundColor);
//        textSize = (int)typedArray.getDimension(R.styleable.input_textSize, TEXT_SIZE);
//        btnSize = (int)typedArray.getDimension(R.styleable.input_btnSize, BTN_SIZE);

//        tvContent.setHint(hint);
//        tvContent.setTextColor(textColor);
//        tvContent.setTextSize(textSize);
        etContent.setHint(hint);
        etContent.setTextColor(textColor);
//        etContent.setTextSize(textSize);
//        tvSend.setTextColor(btnDefaultColor);
//        tvSend.setTextSize(btnSize);
        tvSendPop.setTextColor(btnDefaultColor);
//        tvSendPop.setTextSize(btnSize);

//        ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        etContent.clearFocus();
    }

    /**
     * 开始输入
     */
    public void setInputVisible(int visibility) {
        updateEditTextBodyVisible(visibility);
    }

    public boolean isInputViewAutoHide() {
        return isInputViewAutoHide;
    }

    public void setInputViewAutoHide(boolean inputViewAutoHide) {
        isInputViewAutoHide = inputViewAutoHide;
    }

    /**
     * 针对某些主题下键盘会遮住edittext，调用此方法
     * 初始化评论输入框
     */
    public void initInputBatWindow() {
        final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                bodyLayout.getWindowVisibleDisplayFrame(r);
                //int statusBarH =  getStatusBarHeight();//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
//                if(r.top != statusBarH ){
//                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
//                    r.top = statusBarH;
//                }
                //判断是否存在虚拟键盘，若有，则减去相应高度
                int navigationBar = 0;
                if (CommonUtils.Companion.isNavigationBarShow((Activity) mContext)) {
                    navigationBar = CommonUtils.Companion.getBottomStatusHeight(mContext);
                }
                //int keyboardH = screenH - (r.bottom - r.top);
                int keyboardH = screenH - (r.bottom + navigationBar);//在本app主题之下，不需要减去r.top
                if (keyboardH == currentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }

                currentKeyboardH = keyboardH;

                if (keyboardH < 150) {//说明是隐藏键盘的情况
                    updateEditTextBodyVisible(View.GONE);
                    return;
                }
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rlComment.getLayoutParams();
                lp.setMargins(0, 0, 0, currentKeyboardH);
                rlComment.setLayoutParams(lp);
            }
        });
    }

    private void initEvent() {
        vTransparent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEditTextBodyVisible(View.GONE);
                callBack.onDismiss();
            }
        });

        tvSendPop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etContent.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    callBack.onInput(msg);
                }
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(etContent.getText())) {
                    tvSendPop.setTextColor(btnColor);
                } else {
                    tvSendPop.setTextColor(btnDefaultColor);
                }
            }
        });
    }

    public void setContentMaxEms(int maxEms) {
        etContent.setMaxEms(maxEms);
    }

    public void clearContent() {
        etContent.setText("");
    }

    public String getInput() {
        return etContent.getText().toString();
    }

    //控制评论框显隐
    private void updateEditTextBodyVisible(int visibility) {
        if (isInputViewAutoHide){
            rlComment.setVisibility(visibility);
            if (View.GONE == visibility){
                callBack.onDismiss();
            }
        }
        vTransparent.setVisibility(visibility);

        if (View.VISIBLE == visibility) {
            etContent.requestFocus();
            //弹出键盘
            showSoftInput(etContent.getContext(), etContent);

        } else if (View.GONE == visibility) {
            //隐藏键盘
            hideSoftInput(etContent.getContext(), etContent);
        }
    }

    private static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    private static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public void hideKeyBoard(){
        hideSoftInput(mContext,etContent);
    }

    public OnInputCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(OnInputCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnInputCallBack {
        void onInput(String content);
        void onDismiss();
    }
}
