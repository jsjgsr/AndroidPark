package com.example.a98497.mypark;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    private Activity mActivity;
    private EditText mEdit;
    private KeyboardUtil keyboardUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mActivity = this;
        mEdit = (EditText) findViewById(R.id.id_keyboard);

        mEdit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(keyboardUtil == null){
                    keyboardUtil = new KeyboardUtil(mActivity, mEdit);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                }
                return false;
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(keyboardUtil.isShow()){
                keyboardUtil.hideKeyboard();
            }else{
                finish();
            }
        }
        return false;
    }
}
