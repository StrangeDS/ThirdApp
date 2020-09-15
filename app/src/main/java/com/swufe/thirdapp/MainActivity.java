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

        Button B1 = findViewById(R.id.btn);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView output = findViewById(R.id.oup);
                EditText inpobj = findViewById(R.id.inp);
                final float input = Float.parseFloat(inpobj.getText().toString());
                float res = input*(float)1.8+32;
                output.setText("结果为："+ res +"华氏度");
            }
        });
    }

    public  void btn1(View v){
        final TextView output = findViewById(R.id.oup);
        EditText inpobj = findViewById(R.id.inp);
        final float input = Float.parseFloat(inpobj.getText().toString());
        float res = input*(float)1.8+32;
        output.setText("结果为："+ res +"华氏度");
    }
}