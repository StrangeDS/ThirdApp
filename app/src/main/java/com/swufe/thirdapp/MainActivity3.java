package com.swufe.thirdapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.ls.LSException;

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

public class MainActivity3 extends AppCompatActivity  implements Runnable{
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==7){
                list1 = ( List<HashMap<String,String>>)msg.obj;
                ListView listView = (ListView)findViewById(R.id.list1);
                SimpleAdapter adapter = new SimpleAdapter(MainActivity3.this, list1, R.layout.activity_list_item, new String[] {"ItemTitle", "ItemDetail"}, new int[] {R.id.itemTitle, R.id.itemDetail});
                listView.setAdapter(adapter);
            }
            super.handleMessage(msg);
        }
    };
    List<HashMap<String,String>> list1 = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread t = new Thread(this);
        t.start();
        setContentView(R.layout.activity_main3);
    }

    public void run() {
        Message msg = handler.obtainMessage(7);
        try {
            List<HashMap<String,String>>  rate_list = new ArrayList<HashMap<String,String>>();
            String url = "http://www.usd-cny.com/bankofchina.htm";
            Document doc = Jsoup.connect(url).get();
//            Log.i("TAG", "run: " + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(0);
            //获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                HashMap<String, String> map = new HashMap<String, String>();
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();
//                Log.i("TAG", "run: " + str1 + "==>" + val);
                float v = 100f / Float.parseFloat(val);
                map.put("ItemTitle", str1);
                map.put("ItemDetail", ""+v);
                rate_list.add(map);
                //获取数据并返回……
            }
            msg.obj = rate_list;
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

    private String getRate(String type, String html){
        int i = html.indexOf(type + "</a></td>");
        String str = html.substring(i,i+150);
        str = str.split("<td>")[5].split("</td>")[0];
//        Log.i("TAG", "str: "+ str);
        float rate = 100/Float.parseFloat(str);
        return ""+rate;
    }
}