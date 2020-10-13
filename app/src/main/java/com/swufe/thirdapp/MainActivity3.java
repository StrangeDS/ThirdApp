package com.swufe.thirdapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity3 extends AppCompatActivity  implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==7){
                list1 = ( List<HashMap<String,String>>)msg.obj;
                GridView listView = (GridView)findViewById(R.id.list1);
                adapter = new SimpleAdapter(MainActivity3.this, list1, R.layout.activity_list_item, new String[] {"ItemTitle", "ItemDetail"}, new int[] {R.id.itemTitle, R.id.itemDetail});
                listView.setAdapter(adapter);
                listView.setEmptyView(findViewById(R.id.nodata));
            }
            super.handleMessage(msg);
        }
    };
    List<HashMap<String,String>> list1 = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread t = new Thread(this);
        t.start();
        setContentView(R.layout.activity_main3);
//        ListView listView = (ListView)findViewById(R.id.list1);
        GridView listView = (GridView)findViewById(R.id.list1);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
//        listView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(MainActivity3.this,"长按成功",Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//        });
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
        handler.sendMessage(msg);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        GridView listView = (GridView)findViewById(R.id.list1);
        Object itemAtPosition = listView.getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Intent config = new Intent(this, SingleActivity.class);
        config.putExtra("type", titleStr);
        config.putExtra("rate", detailStr);
        startActivity(config);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
//        Toast.makeText(this,"长按成功",Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("是否删除选中汇率？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GridView listView = (GridView)findViewById(R.id.list1);
                        list1.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("否", null);
        builder.create().show();
        return true;
    }
}