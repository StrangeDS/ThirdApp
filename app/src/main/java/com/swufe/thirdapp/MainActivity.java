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
    }

    public  void btn1add1(View v){
        TextView score = findViewById(R.id.score1);
        String res = score.getText().toString();
        int score_new = Integer.parseInt(res) + 1;
        score.setText("" + score_new);
    }

    public  void btn1add2(View v){
        TextView score = findViewById(R.id.score1);
        String res = score.getText().toString();
        int score_new = Integer.parseInt(res) + 2;
        score.setText("" + score_new);
    }

    public  void btn1add3(View v){
        TextView score = findViewById(R.id.score1);
        String res = score.getText().toString();
        int score_new = Integer.parseInt(res) + 3;
        score.setText("" + score_new);
    }

    public  void btn2add1(View v){
        TextView score = findViewById(R.id.score2);
        String res = score.getText().toString();
        int score_new = Integer.parseInt(res) + 1;
        score.setText("" + score_new);
    }

    public  void btn2add2(View v){
        TextView score = findViewById(R.id.score2);
        String res = score.getText().toString();
        int score_new = Integer.parseInt(res) + 2;
        score.setText("" + score_new);
    }

    public  void btn2add3(View v){
        TextView score = findViewById(R.id.score2);
        String res = score.getText().toString();
        int score_new = Integer.parseInt(res) + 3;
        score.setText("" + score_new);
    }

    public  void btn0(View v){
        TextView score1 = findViewById(R.id.score1);
        TextView score2 = findViewById(R.id.score2);
        score1.setText("0");
        score2.setText("0");
    }
}