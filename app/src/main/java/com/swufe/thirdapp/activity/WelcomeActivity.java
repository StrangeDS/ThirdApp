package com.swufe.thirdapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.swufe.thirdapp.R;
import com.swufe.thirdapp.ui.login.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGHT = 1500;  //延迟
    private boolean boolHaveLogin = false;
    private String username = null;
    private SharedPreferences sp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sp = getSharedPreferences("loginstate", MODE_PRIVATE);
        boolHaveLogin = Boolean.parseBoolean(sp.getString("boolHaveLogin", "False"));
        username = sp.getString("username", "");
        if(boolHaveLogin & (!username.equals(""))){
            Toast.makeText(this, "您已登录，正在加载中……", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    WelcomeActivity.this.startActivity(intent);
                    WelcomeActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGHT);
        }
        else {
            Toast.makeText(this, "加载中……", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    WelcomeActivity.this.startActivity(intent);
                    WelcomeActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGHT);
        }
    }
}