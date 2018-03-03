package com.example.a98497.mypark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editText,editText11,editText12,editText13,editText14;

    private boolean b = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number);
        Thread.setDefaultUncaughtExceptionHandler(new Uncath());
        //		editText = (EditText) this.findViewById(R.id.editText1);
        editText11 = (EditText) this.findViewById(R.id.editText11);
        editText12 = (EditText) this.findViewById(R.id.editText12);
        editText13 = (EditText) this.findViewById(R.id.editText13);
        editText14 = (EditText) this.findViewById(R.id.editText14);

        editText11.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if(arg0.length() > 3)
                {
                    editText12.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });


        editText12.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if(arg0.length() > 3)
                {
                    editText13.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        editText13.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if(arg0.length() > 3)
                {
                    editText14.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

    }
}
