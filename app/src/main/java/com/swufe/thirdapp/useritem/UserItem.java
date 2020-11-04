package com.swufe.thirdapp.useritem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.swufe.thirdapp.R;

public class UserItem extends androidx.constraintlayout.widget.ConstraintLayout {
    public UserItem(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.useritem, this);
        TextView username = findViewById(R.id.username);
        Button btn_delete = findViewById(R.id.delete);
        btn_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
