package com.swufe.thirdapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.swufe.thirdapp.R;
import com.swufe.thirdapp.avatar.Avatar;
import com.swufe.thirdapp.avatar.CircleDrawable;
import com.swufe.thirdapp.ui.login.LoginActivity;

import java.lang.reflect.Method;

public class MainActivity extends FragmentActivity {

    //是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useThemestatusBarColor = false;
    //是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
    protected boolean useStatusBarColor = true;

    private Fragment mFragments[] = null;
    private RadioGroup radioGroup = null;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
    private RadioButton rbtHome = null,rbtFriends = null,rbtExtern = null;
    private Button left_btn = null,btn_logout = null;
    private Toolbar toolbar = null;
    private String username = "";
    private DrawerLayout drawerLayout = null;
    private Intent intent = null;
    private Avatar avatar = null;
    private CircleDrawable circleDrawable = null;
    private TextView title = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    //据说下面这个重写能显示icon，但我并没有成功
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();//透明标题栏
        setContentView(R.layout.activity_main);

        //获得传递数据username
        intent = getIntent();
        username = intent.getStringExtra("username");
        if(username.equals("")){
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
        }

        //主布局数据加载
        avatar = new Avatar(username);
        if(avatar.getDrawable() == null) {
            avatar.setName("1023221456");
        }
        circleDrawable = new CircleDrawable(avatar.getDrawable(), MainActivity.this, 44);//继承Drawable类，用处是将图片进行规定大小的圆形处理。
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);//描绘侧边栏按钮图案
        title = (TextView)findViewById(R.id.toolbar_title);
        title.setVisibility(title.INVISIBLE);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.add);
        toolbar.setNavigationIcon(circleDrawable);
        toolbar.setTitle(username);
        toolbar.setSubtitle("在线~");
        RadioButton btn_news = (RadioButton)findViewById(R.id.button_home);
        btn_news.setChecked(true);

        //侧边栏数据加载
        TextView left_username = (TextView) findViewById(R.id.left_username);
        left_username.setText(username);
        RoundedImageView roundedImageView = (RoundedImageView) findViewById(R.id.left_avatar);
        roundedImageView.setImageBitmap(avatar.getBitmap());

        //监听：侧边栏按钮，菜单，注销按钮
        //菜单按钮
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        //侧边栏
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        //菜单选项
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_item:
                        break;
                    case R.id.remove_item:
                        Toast.makeText(MainActivity.this, "菜单里面没有彩蛋", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        //注销按钮
        left_btn = (Button)findViewById(R.id.left_btn);
        left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("TAG","奇了怪了");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("注销登录").setMessage("是否退出当前账号?").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sp = getSharedPreferences("loginstate", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("boolHaveLogin", "false");
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                }).setNegativeButton("否", null);
                builder.create().show();
            }
        });

        //fragment管理
        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragment_main);
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragment_friends);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragment_extern);
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();
        rbtHome = (RadioButton)findViewById(R.id.button_home);
        rbtFriends = (RadioButton)findViewById(R.id.button_friends);
        rbtExtern = (RadioButton)findViewById(R.id.button_extern);
        changeImageSize();//图片大小修正
        radioGroup = (RadioGroup)findViewById(R.id.button_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("radioGroup", "checkId=" + checkedId);
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                switch(checkedId){
                    case R.id.button_home:
                        title.setVisibility(title.INVISIBLE);
                        title.setText("");
                        fragmentTransaction.show(mFragments[0]).commit();
                        break;
                    case R.id.button_friends:
                        title.setVisibility(title.VISIBLE);
                        title.setText("联系人");
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;
                    case R.id.button_extern:
                        title.setVisibility(title.VISIBLE);
                        title.setText("动态");
                        fragmentTransaction.show(mFragments[2]).commit();
                        break;
                    default:
                        break;
                }
            }});
    }

    //状态栏透明函数
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    //重新设置RadioButton图片大小，无奈之举
    private void changeImageSize() {
        Drawable drawableFirst = getResources().getDrawable(R.drawable.btn_news);
        drawableFirst.setBounds(0, 0, 90, 120);//x,y，宽，高
        rbtHome.setCompoundDrawables(null, drawableFirst, null, null);//没有文本，任意放

        Drawable drawableSecond = getResources().getDrawable(R.drawable.btn_friends);
        drawableSecond.setBounds(0, 0, 90, 120);
        rbtFriends.setCompoundDrawables(null, drawableSecond, null, null);

        Drawable drawableThird = getResources().getDrawable(R.drawable.btn_extern);
        drawableThird.setBounds(0, 0, 90, 120);
        rbtExtern.setCompoundDrawables(null, drawableThird, null, null);
    }
}
