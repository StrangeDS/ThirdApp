package com.swufe.thirdapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class SingleActivity extends AppCompatActivity {
    float rate;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        Intent intent = getIntent();
        title = intent.getStringExtra("type");
        rate = Float.parseFloat(intent.getStringExtra("rate"));
        TextView type = (TextView)findViewById(R.id.type);
        type.setText(title);
        EditText input = (EditText)findViewById(R.id.inp);
        input.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            EditText input = (EditText)findViewById(R.id.inp);
            TextView result = (TextView)findViewById(R.id.res);
            float f = Float.parseFloat(input.getText().toString());
            result.setText(""+f * rate);
        }
    };
}