package com.swufe.thirdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.swufe.thirdapp.main.Main;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class Test extends AppCompatActivity {

    private List<Map<String,Object>> usersaved = null;
    private SimpleAdapter adapter = null;
    Main m = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        m = findViewById(R.id.mmm);
        usersaved = getDat();
        Log.i("TAG",""+usersaved.size());
        adapter = new SimpleAdapter(this, usersaved, R.layout.useritem, new String[]{"image", "text"}, new int[]{R.id.avatar, R.id.username});
        //控件与适配器绑定
        m.setAdapter(adapter);
        //点击事件
        m.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("TAG", "FUCK!");
                Toast.makeText(Test.this,"可恶啊", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private List<Map<String,Object>> getDat() {
        List<Map<String,Object>> d = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("image", R.drawable.avatar);
        map.put("text", "北京");
        d.add(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("image", R.drawable.avatar);
        map1.put("text", "上海");
        d.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("image", R.drawable.avatar);
        map2.put("text", "廣州");
        d.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("image", R.drawable.avatar);
        map3.put("text", "深圳");
        d.add(map3);
        return d;
    }
}