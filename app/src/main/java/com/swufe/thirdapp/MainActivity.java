package com.swufe.thirdapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Runnable{
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==5){
                HashMap<String, Float> rate =  (HashMap<String, Float>)msg.obj;
//                Log.i("TAG", "handleMessage: getMessage msg = "+str);
                dollar = rate.get("美元");
                pound = rate.get("英镑");
                euro = rate.get("欧元");
                sp = getSharedPreferences("rate", MODE_PRIVATE);
//                Log.i("TAG", "rate = " + dollar + "," + pound + "," + euro);
                Toast.makeText(MainActivity.this, "从网络更新数据中……", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sp.edit();
                editor.putFloat("dollar_rate", dollar);
                editor.putFloat("pound_rate", pound);
                editor.putFloat("euro_rate", euro);
                editor.apply();
                Toast.makeText(MainActivity.this, "已完成更新", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 1000 * 60 * 60 * 24);// 间隔24小时
        }
        void update() {
            //刷新msg的内容
            Thread t = new Thread(MainActivity.this);
            t.start();
        }
    };
    URL url;
    float dollar;
    float pound;
    float euro;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("rate", MODE_PRIVATE);
        Toast.makeText(this, "读取本地数据", Toast.LENGTH_SHORT).show();
        dollar = sp.getFloat("dollar_rate", 1);
        pound = sp.getFloat("pound_rate", 1);
        euro = sp.getFloat("euro_rate", 1);
        Log.i("TAG", "rate = " + dollar + "," + pound + "," + euro);
        handler.postDelayed(runnable, 1000 * 60 * 60 * 24);//24小时之后
    }

    public  void btn(View v){
        TextView input = findViewById(R.id.inp);
        TextView output = findViewById(R.id.outp);
        if(input.getText().toString().isEmpty()) {
            //no input
            Toast.makeText(this, "请输入人民币金额", Toast.LENGTH_SHORT).show();
        }else{
            String str = input.getText().toString();
            Float result;
            if(v.getId() == R.id.btn1){
                result = Float.parseFloat(str) * dollar;
                output.setText("" + result);
            }else if(v.getId() == R.id.btn2){
                result = Float.parseFloat(str) * pound;
                output.setText("" + result);
            }else if(v.getId() == R.id.btn3){
                result = Float.parseFloat(str) * euro;
                output.setText("" + result);
            }
        }
    }

    public  void btn_conifg(View v){
        Intent config = new Intent(this, MainActivity2.class);
        config.putExtra("dollar_rate", dollar);
        config.putExtra("pound_rate", pound);
        config.putExtra("euro_rate", euro);
        startActivityForResult(config,1);
    }

    public  void btn_show(View v){
        Intent config = new Intent(this, MainActivity3.class);
        startActivity(config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==2){
            Bundle rate = data.getExtras();
            Toast.makeText(this, "配置更新中", Toast.LENGTH_SHORT).show();
            dollar = rate.getFloat("dollar_rate", 0.0f);
            pound = rate.getFloat("pound_rate", 0.0f);
            euro = rate.getFloat("euro_rate", 0.0f);
            Log.i("TAG","dollar_rate=" + dollar);
            Toast.makeText(this, "配置已更新", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Message msg = handler.obtainMessage(5);
        HashMap<String,Float> rate = new HashMap<String,Float>();
        try{
            String url = "http://www.usd-cny.com/bankofchina.htm";
            Document doc = Jsoup.connect(url).get();
//            Log.i("TAG", "run: " + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(0);
            //获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();
//                Log.i("TAG", "run: " + str1 + "==>" + val);
                float v = 100f / Float.parseFloat(val);
                if(str1.equals("美元") || str1.equals("英镑") || str1.equals("欧元")){
                    rate.put(str1, v);
                }
                //获取数据并返回……
            }
            msg.obj = rate;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        自写的文本处理（已废弃）
//        try {
//            url = new URL("http://www.usd-cny.com/bankofchina.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//            String html = inputStream2String(in);
//            html = html.replaceAll("[\\s\\t\\n\\r]", "");
//            html = getRate("美元", html) + "," + getRate("英镑", html) + "," + getRate("欧元", html);
////            Log.i("TAG","run:html = "+html);
//            msg.obj = html;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        msg.obj = "Hello from run()";
        handler.sendMessage(msg);
    }

    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String str = ((TextView)findViewById(R.id.inp)).getText().toString();
        if (str.isEmpty()){
            str = "0";
        }
        float num = Float.parseFloat(str);
//        Log.i("TAG", "已旋转");
        outState.putFloat("num", num);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        float num = savedInstanceState.getFloat("num");
        ((TextView)findViewById(R.id.inp)).setText(""+num);
    }

//    废弃函数
//    private String getRate(String type, String html){
//        int i = html.indexOf(type + "</a></td>");
//        String str = html.substring(i,i+150);
//        str = str.split("<td>")[5].split("</td>")[0];
////        Log.i("TAG", "str: "+ str);
//        float rate = 100/Float.parseFloat(str);
//        return ""+rate;
//    }
}