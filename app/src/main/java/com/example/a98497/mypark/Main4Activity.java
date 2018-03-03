package com.example.a98497.mypark;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.Map;

import test.HttpClientUtils;

import static android.R.attr.data;


public class Main4Activity extends AppCompatActivity {
    private EditText ed;
    private Button btn;
    private Button toScan;
    private TextView tvResult;
    private static final int REQ_CODE_PERMISSION = 0x1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ed = (EditText) findViewById(R.id.password);
        btn = (Button)findViewById(R.id.btn);
        tvResult  = (TextView) findViewById(R.id.tv_result);
        toScan = (Button)findViewById(R.id.scan);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发给服务器指令，查询转台、传过去id去查数据库，对比密码
                //密码一致，发给上位机指令，上位机返回指令后，再讲指令返，提示取车成功
                String pwd =ed.getText().toString().trim();
                ///////////////////////线程////////////////////
                Handler handler = new Handler() {

                    public void handleMessage(Message msg) {
                        //获取data，data是从服务器端获取的数据（数据以Jason串的形式传到工具类，在工具类里封装成 ArrayList<Map<String,String>>）
                        ArrayList<Map<String, String>> data = (ArrayList<Map<String, String>>) msg.getData().getSerializable("res");
                        Toast.makeText(Main4Activity.this,data.toString(),Toast.LENGTH_SHORT).show();
                        if(data.get(0).get("connectInfo") !=null){
                            Toast.makeText(Main4Activity.this,"请检查网络连接",Toast.LENGTH_LONG).show();
                        }else{
                            //data===[{"uid":"6","uname":"a","upwd":"a"},{awdqeqwe}]
                            //{"reUser":{"uid":"6","uname":"a","upwd":"a"}}
                            String state = data.get(0).get("isRight");
                            if(state.equals("0")){//密码错误
                                showNormalDialog2();
                            }
                            else {//密码正确。跳转。传递参数

                                String time = data.get(1).get("time");
                                Toast.makeText(Main4Activity.this,"时间是===" + time,Toast.LENGTH_LONG).show();
                                String[] arr = time.split("\\.");
                                Toast.makeText(Main4Activity.this,"得到的分钟===" + arr[0],Toast.LENGTH_LONG).show();
                                String pwd =ed.getText().toString().trim();
                                Intent intent = new Intent(Main4Activity.this,PayActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("pwd", pwd);
                                bundle.putSerializable("time", arr[0]);//存车时长
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }



                    }
                };
                ////////////////////////线程/////////////////////
                //将前台获取的姓名和密码，拼成访问服务器的地址字符串

                try{
                    String url = getResources().getString(R.string.url) + "/carservice/isRight/" + pwd;

                    HttpClientUtils htc = new HttpClientUtils();//调用工具类

                    htc.setUrl(url);//设置访问后台（服务器）路径
                    htc.setHandler(handler); //调用线程

                    htc.start(); //开启线程

                    htc.interrupt();//开启后要暂停
                }catch(Exception e){
                    Toast.makeText(Main4Activity.this,"请检测网络连接",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    /**
     * 点击扫一扫按钮，开启扫描二维码
     * @param view
     */
    public void startScan(View view){
        //跳转到扫一扫
        startActivityForResult(new Intent(Main4Activity.this, CaptureActivity.class),0);
    }
    //返回结果的分析
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            ed.setText(result);
        }
    }
    private void showNormalDialog0(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(Main4Activity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("保存成功");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        ed.setText("");
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    private void showNormalDialog1(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(Main4Activity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("修改错误");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        ed.setText("");
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    private void showNormalDialog2(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(Main4Activity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("不存在密码");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        ed.setText("");
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    private void showNormalDialog3(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(Main4Activity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("上位机出错");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        ed.setText("");
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}
