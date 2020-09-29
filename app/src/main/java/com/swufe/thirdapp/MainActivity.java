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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Runnable{
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==5){
                String str =  (String)msg.obj;
//                Log.i("TAG", "handleMessage: getMessage msg = "+str);
                dollar = Float.parseFloat(str.split(",")[0]);
                pound = Float.parseFloat(str.split(",")[1]);
                euro = Float.parseFloat(str.split(",")[2]);
            }
            super.handleMessage(msg);
        }
    };
    URL url;
    float dollar;
    float pound;
    float euro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("rate", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        Thread t = new Thread(this);
        t.start();
        Toast.makeText(this, "已更新实时数据", Toast.LENGTH_SHORT).show();
        dollar = sp.getFloat("dollar_rate", dollar);
        pound = sp.getFloat("pound_rate", pound);
        euro = sp.getFloat("euro_rate", euro);
        Toast.makeText(this, "已配置本地数据", Toast.LENGTH_SHORT).show();
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
        try {
            url = new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputStream2String(in);
            html = html.replaceAll("[\\s\\t\\n\\r]", "");
            html = getRate("美元", html) + "," + getRate("英镑", html) + "," + getRate("欧元", html);
//            Log.i("TAG","run:html = "+html);
            msg.obj = html;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        float num = Float.parseFloat(((TextView)findViewById(R.id.inp)).getText().toString());
        Log.i("TAG", "已旋转");
        outState.putFloat("num", num);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        float num = savedInstanceState.getFloat("num");
        ((TextView)findViewById(R.id.inp)).setText(""+num);
    }

    private String getRate(String type, String html){
        int i = html.indexOf(type + "</a></td>");
        String str = html.substring(i,i+150);
        str = str.split("<td>")[5].split("</td>")[0];
//        Log.i("TAG", "str: "+ str);
        float rate = 100/Float.parseFloat(str);
        return ""+rate;
    }
}