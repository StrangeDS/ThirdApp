package com.swufe.thirdapp;

import androidx.appcompat.app.AlertDialog;
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

    public  void btn1(View v){
        TextView input = findViewById(R.id.inp);
        TextView output = findViewById(R.id.outp);
        Button b = findViewById(R.id.btn1);
        String str = input.getText().toString();
        if(str.isEmpty()){
            output.setText("0");
        }
        else{
            float result = Float.parseFloat(str);
            float rate = Float.parseFloat(b.getText().toString().split(":")[1]);
            result = result * rate;
            output.setText("" + result);
        }
    }

    public  void btn2(View v){
        TextView input = findViewById(R.id.inp);
        TextView output = findViewById(R.id.outp);
        Button b = findViewById(R.id.btn2);
        String str = input.getText().toString();
        if(str.isEmpty()){
            output.setText("0");
        }
        else{
            float result = Float.parseFloat(str);
            float rate = Float.parseFloat(b.getText().toString().split(":")[1]);
            result = result * rate;
            output.setText("" + result);
        }
    }

    public  void btn3(View v){
        TextView input = findViewById(R.id.inp);
        TextView output = findViewById(R.id.outp);
        Button b = findViewById(R.id.btn3);
        String str = input.getText().toString();
        if(str.isEmpty()){
            output.setText("0");
        }
        else{
            float result = Float.parseFloat(str);
            float rate = Float.parseFloat(b.getText().toString().split(":")[1]);
            result = result * rate;
            output.setText("" + result);
        }
    }
}