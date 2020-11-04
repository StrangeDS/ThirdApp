package com.swufe.thirdapp.ui.login;

import android.app.Activity;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.swufe.thirdapp.MainActivity;
import com.swufe.thirdapp.R;
import com.swufe.thirdapp.adapter.SavedAdapter;
import com.swufe.thirdapp.avatar.Avatar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private SharedPreferences sp = null;
    private String userList = null;
    private String lastUser = null;
    private String lastPassword = null;
    private String username = null;
    private String password = null;
    private ListView list = null;
    private Button btn_list = null;
    private Button btn_delete = null;
    private Button btn_passvisible = null;
    private EditText usernameEditText = null;
    private EditText passwordEditText = null;
    private RoundedImageView roundedImageView = null;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private ArrayList<HashMap<String,String>> usersaved = null;
    private SavedAdapter adapter = null;
    private boolean flag_passvisible = false;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);


        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(LoginActivity.this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        btn_list = findViewById(R.id.btn_list);
        list = findViewById(R.id.saved_list);
        list.setVisibility(list.INVISIBLE);
        roundedImageView = findViewById(R.id.roundedImageView);
        btn_delete = findViewById(R.id.btn_delete);
        btn_passvisible = findViewById((R.id.btn_passvisible));

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar, null);
//        Avatar a1 = new Avatar("1023221456");
//        a1.saveImage(bitmap);

        sp = getSharedPreferences("loginstate", MODE_PRIVATE);
        lastUser = sp.getString("username", "");
        sp = getSharedPreferences(lastUser, MODE_PRIVATE);
        lastPassword = sp.getString("password", "");
        Log.i("TAG", "lastUser:"+lastUser);
        Log.i("TAG", "lastPassword"+lastPassword);
        Avatar a1 = new Avatar(lastUser);
        Bitmap bitmap = a1.getBitmap();
        if ((!lastUser.isEmpty()) & (!lastPassword.isEmpty())) {
            Log.i("TAG", "初始化了的");
            btn_list.setVisibility(btn_list.VISIBLE);
            usernameEditText.setText(lastUser);
            passwordEditText.setText(lastPassword);
            roundedImageView.setImageBitmap(bitmap);
        }

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameEditText.setText("");
                passwordEditText.setText("");
                roundedImageView.setImageBitmap(null);
            }
        });
        if(!usernameEditText.getText().toString().equals("")){
            btn_delete.setVisibility(btn_delete.VISIBLE);
        }
        else{
            btn_delete.setVisibility(btn_delete.INVISIBLE);
        }
        btn_passvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag_passvisible){
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    Log.i("TAG","???"+flag_passvisible);
                    flag_passvisible = false;
                }
                else{
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    Log.i("TAG","???"+flag_passvisible);
                    flag_passvisible = true;
                }
            }
        });

        usersaved = getData();
        adapter = new SavedAdapter(this, R.layout.useritem, usersaved);
        //控件与适配器绑定
        list.setAdapter(adapter);
        //点击事件
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String info=adapterView.getItemAtPosition(i).toString();//获取i所在的文本
//                Toast.makeText(LoginActivity.this,info,Toast.LENGTH_SHORT).show();
                usernameEditText.setText(  ((HashMap<String, String>)adapter.getItem(i)).get("username"));
                passwordEditText.setText(  ((HashMap<String, String>)adapter.getItem(i)).get("password"));
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.getVisibility() == list.VISIBLE){
                    list.setVisibility(list.INVISIBLE);
//            Toast.makeText(getApplicationContext(), "被点了", Toast.LENGTH_LONG).show();
                }
                else {
                    list.setVisibility(list.VISIBLE);
//            Toast.makeText(getApplicationContext(), "被点了", Toast.LENGTH_LONG).show();
                }
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(true);
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                if(!usernameEditText.getText().toString().equals("")){
                    btn_delete.setVisibility(btn_delete.VISIBLE);
                }
                else{
                    btn_delete.setVisibility(btn_delete.INVISIBLE);
                }
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
//        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        // TODO : initiate successful logged in experience
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        sp = getSharedPreferences("loginstate", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("boolHaveLogin","true");
        editor.putString("username", username);
        editor.apply();
        sp = getSharedPreferences(username, MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("password", password.toString());
        editor.apply();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("username", username);
        LoginActivity.this.startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<HashMap<String, String>> getData() {
        ArrayList<HashMap<String, String>> d = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
//        map.put("image", R.drawable.avatar);
        map.put("username", "北京");
        map.put("password", "BeiJing");
        d.add(map);
        HashMap<String, String> map1 = new HashMap<>();
//        map1.put("image", R.drawable.avatar);
        map1.put("username", "上海");
        map1.put("password", "ShangHai");
        d.add(map1);
        HashMap<String, String> map2 = new HashMap<>();
//        map2.put("image", R.drawable.avatar);
        map2.put("username", "廣州");
        map2.put("password", "GuangZhou");
        d.add(map2);
        HashMap<String, String> map3 = new HashMap<>();
//        map3.put("image", R.drawable.avatar);
        map3.put("username", "深圳");
        map3.put("password", "ShenZhen");
        d.add(map3);
        return d;
    }
}