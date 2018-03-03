package test;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.a98497.mypark.Main4Activity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gsr on 2015/4/9.
 */
public class HttpClientUtils extends Thread {
    private String url = "";
    private Handler handler = null;
    public Context c = null;

    public  void getAvailableMemory(Context context){
        c = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        //获取地址

        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        Message message = new Message();
        Bundle bundle = new Bundle();
        message.setData(bundle);

        ArrayList<Map<String,String>> data = new ArrayList<Map<String, String>>();
        try{
            HttpClient client = new DefaultHttpClient();
            response = client.execute(request);
            if(response.getStatusLine().getStatusCode() == 200){
                String resultStr = EntityUtils.toString(response.getEntity(),"UTF-8");//汉子要转字符集


                //"{\"isReName\":[{\"state\":\"1\"}]}"
                /*
                * {"reUser":
                * [{"uid":"1","uname":"w","upwd":"w"},
                * {"uid":"2","uname":"1","upwd":"1"},
                * {"uid":"3","uname":"qssss","upwd":"qasd"},
                * {"uid":"4","uname":"qq","upwd":"qq"},
                * {"uid":"5","uname":"e","upwd":"e"},
                * {"uid":"6","uname":"a","upwd":"a"},
                * {"uid":"7","uname":"d","upwd":"d"}]
                * }
                * */
                JSONObject jsonObject = new JSONObject(resultStr);//
                Iterator<String> keys = jsonObject.keys();//获得key获得对象第一个isRename
                if (keys.hasNext()){
                    String str = jsonObject.getString(keys.next());//获得login后面的数据

                    if(str.indexOf("[") == 0){
                        JSONArray jsonArray = new JSONArray(str);//login的下的数组
                        for(int i = 0;i < jsonArray.length() ; i++){
                            Map<String,String> jsonMap = new HashMap<String, String>();
                            JSONObject temp = new JSONObject(jsonArray.get(i).toString());//获得values里的key值
                            Iterator<String> tempKeys = temp.keys();
                            while(tempKeys.hasNext()){
                                String key = tempKeys.next();
                                jsonMap.put(key,temp.getString(key));//获得数组里面的values值
                            }
                            data.add(jsonMap);
                        }
                    }else{
                        //{"reUser":{"uid":"6","uname":"a","upwd":"a"}}
                        Map<String,String> jsonMap = new HashMap<String,String>();
                        JSONObject temp = new JSONObject(str);//str = {"uid":"6","uname":"a","upwd":"a"}
                        Iterator<String> tempKeys = temp.keys();
                        while(tempKeys.hasNext()){
                            String key = tempKeys.next();
                            jsonMap.put(key,temp.getString(key));
                        }
                        data.add(jsonMap);
                    }



                }
              // System.out.println("sssss ----" + data.get(0).get("state").toString());
              //  System.out.println(" state------- " + data.get("state").toString());
                bundle.putSerializable("res",data);
                handler.sendMessage(message);
                //result有值
            }
        }catch (Exception e){
            Map<String,String> info = new HashMap<String, String>();
            info.put("connectInfo","null");
            data.add(info);

            bundle.putSerializable("res",data);
            handler.sendMessage(message);

            e.printStackTrace();

        }
    }
}
