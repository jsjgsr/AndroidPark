package com.example.a98497.mypark;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import test.HttpClientUtils;

public class PayActivity extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnBack;
    private TextView info;
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public  double div(double v1, double v2, int scale) {//计算时间
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        info = (TextView)findViewById(R.id.info);
        String time = getIntent().getStringExtra("time");

        //计算时间
        int hour = Integer.parseInt(time);
         double TTT = div(hour,60,2);
        final double zheng = Math.ceil(TTT);


        info.setText("存车时长为：" + TTT + "小时，应付"+ zheng*4 + "元");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发给服务器指令，查询转台、传过去id去查数据库，对比密码
                //密码一致，发给上位机指令，上位机返回指令后，再讲指令返，提示取车成功
                String pwd = getIntent().getStringExtra("pwd");
                ///////////////////////线程////////////////////
                Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        //获取data，data是从服务器端获取的数据（数据以Jason串的形式传到工具类，在工具类里封装成 ArrayList<Map<String,String>>）
                        ArrayList<Map<String, String>> data = (ArrayList<Map<String, String>>) msg.getData().getSerializable("res");
                        Toast.makeText(PayActivity.this,data.toString(),Toast.LENGTH_SHORT).show();
                        //data===[{"uid":"6","uname":"a","upwd":"a"},{awdqeqwe}]
                        //{"reUser":{"uid":"6","uname":"a","upwd":"a"}}
                        String state = data.get(0).get("wantCar");
                        if(state.equals("0")){
                            showNormalDialog0();
                        }
                        else if(state.equals("1")){
                            showNormalDialog1();
                        }
                        else if(state.equals("2")){
                            showNormalDialog2();
                        }
                        else if(state.equals("3")){
                            showNormalDialog3();
                        }

                    }
                };
                ////////////////////////线程/////////////////////
                //将前台获取的姓名和密码，拼成访问服务器的地址字符串

                String url = getResources().getString(R.string.url) + "/carservice/wantCar/" + pwd +","+ zheng*4;

                HttpClientUtils htc = new HttpClientUtils();//调用工具类

                htc.setUrl(url);//设置访问后台（服务器）路径

                htc.setHandler(handler); //调用线程

                htc.start(); //开启线程

                htc.interrupt();//开启后要暂停
            }
        });
    }
    private void showNormalDialog0(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(PayActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("支付成功，正在取车");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        Intent intent = new Intent(PayActivity.this,Main4Activity.class);
                        startActivity(intent);
                        finish();
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
                new AlertDialog.Builder(PayActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("支付失败");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
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
                new AlertDialog.Builder(PayActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("不存在密码");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
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
                new AlertDialog.Builder(PayActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("上位机出错");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
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
