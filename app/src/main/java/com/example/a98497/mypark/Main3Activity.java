package com.example.a98497.mypark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        transaction.replace(R.id.main_content, new MFragment(), "1");
        transaction.commitAllowingStateLoss();
    }
}
