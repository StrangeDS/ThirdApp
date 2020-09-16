package com.swufe.thirdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView score = findViewById(R.id.score);
    }

    public  void btn1(View v){
        TextView score = findViewById(R.id.score);
        String res = score.getText().toString().split(":")[0];
        int score_new = Integer.parseInt(score.getText().toString().split(":")[1]) + 1;
        score.setText(res +":"+ score_new);
    }

    public  void btn2(View v){
        TextView score = findViewById(R.id.score);
        String res = score.getText().toString().split(":")[0];
        int score_new = Integer.parseInt(score.getText().toString().split(":")[1]) + 2;
        score.setText(res +":"+ score_new);
    }

    public  void btn3(View v){
        TextView score = findViewById(R.id.score);
        String res = score.getText().toString().split(":")[0];
        int score_new = Integer.parseInt(score.getText().toString().split(":")[1]) + 3;
        score.setText(res +":"+ score_new);
    }

    public  void btn0(View v){
        TextView score = findViewById(R.id.score);
        String res = score.getText().toString().split(":")[0];
        score.setText(res +":0");
    }

    public  void btnr1(View v){
        TextView score = findViewById(R.id.score);
        String res = score.getText().toString().split(":")[0];
        int score_new = Integer.parseInt(score.getText().toString().split(":")[1]) - 1;
        if(score_new < 0){
            score_new = 0;
        }
        score.setText(res +":"+ score_new);
    }
}