package com.swufe.thirdapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.swufe.thirdapp.R;
import com.swufe.thirdapp.adapter.TalkAdapter;
import com.swufe.thirdapp.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Talk extends AppCompatActivity implements Runnable {
    private Intent intent = null;
    private String name = "";
    private Toolbar toolbar = null;
    private TextView title = null;
    private ListView talkList = null;
    private ArrayList<HashMap<String, String>> data = null;
    private TalkAdapter adapter = null;
    private Button submit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();//透明标题栏
        setContentView(R.layout.activity_talk);

        //消息记录list填充
        Thread t = new Thread(Talk.this);
        t.run();

//        界面初始化
        intent = getIntent();
        name = intent.getStringExtra("name");
        if(name == null){
            name = "陌生人";
        }
        toolbar = (Toolbar)findViewById(R.id.talk_toolbar);
        title = (TextView)findViewById(R.id.name);
        title.setText(name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        返回最后一条消息
                intent = new Intent();
                intent.putExtra("new_text", ((HashMap<String, String>)adapter.getItem(adapter.getCount()-1)).get("text"));
                setResult(1,intent);
                Talk.this.finish();
            }
        });
        submit =findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text =findViewById(R.id.input);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("flag", "0");
                map.put("name", "极端");
                map.put("text", text.getText().toString());
                data.add(map);
                adapter.notifyDataSetChanged();
                text.setText("");
            }
        });

    }

    //状态栏透明函数
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (false) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));//设置状态栏背景色
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && true) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private ArrayList<HashMap<String, String>> getData(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap m1 = new HashMap();
        m1.put("flag", "1");
        m1.put("text", "HHHHHHH");
        m1.put("name", "张三");
        HashMap m2 = new HashMap();
        m2.put("flag", "1");
        m2.put("text", "HHGGHH");
        m2.put("name", "李四");
        HashMap m3 = new HashMap();
        m3.put("flag", "0");
        m3.put("text", "DDDDDDDDDDDDD");
        m3.put("name", "王五");
        HashMap m4 = new HashMap();
        m4.put("flag", "0");
        m4.put("text", "BBBBBBBBBBB");
        m4.put("name", "赵六");
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        return list;
    }

    @Override
    public void run() {
        talkList = findViewById(R.id.talk_list);
        data = getData();
        adapter = new TalkAdapter(this, data, R.layout.talk_item_left, R.layout.talk_item_right);
        talkList.setAdapter(adapter);
    }
}