package com.swufe.thirdapp.ui.login;

import android.app.Activity;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.swufe.thirdapp.MainActivity;
import com.swufe.thirdapp.R;
import com.swufe.thirdapp.WelcomeActivity;
import com.swufe.thirdapp.avatar.Avatar;
import com.swufe.thirdapp.ui.login.LoginViewModel;
import com.swufe.thirdapp.ui.login.LoginViewModelFactory;

import java.net.MalformedURLException;
import java.net.URL;
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
    private Spinner spinner = null;
    private Button btn_spinner = null;
    private Button btn_delete = null;
    private Button btn_passvisible = null;
    private EditText usernameEditText = null;
    private EditText passwordEditText = null;
    private RoundedImageView roundedImageView = null;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private List<Map<String,Object>> usersaved = null;
    private SimpleAdapter adapter = null;
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


        btn_spinner = findViewById(R.id.btn_spinner);
        spinner = findViewById(R.id.spinner);
        roundedImageView = findViewById(R.id.roundedImageView);
        btn_delete = findViewById(R.id.btn_delete);
        btn_passvisible = findViewById((R.id.btn_passvisible));

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar, null);
//        Avatar a1 = new Avatar("1023221456");
//        a1.saveImage(bitmap);

        sp = getSharedPreferences("last_login", MODE_PRIVATE);
        lastUser = sp.getString("username", "");
        sp = getSharedPreferences(lastUser, MODE_PRIVATE);
        lastPassword = sp.getString("password", "");
        Avatar a1 = new Avatar(lastUser);
        Bitmap bitmap = a1.getBitmap();
        if ((!lastUser.isEmpty()) & (!lastPassword.isEmpty())) {
            btn_spinner.setVisibility(btn_spinner.VISIBLE);
            usernameEditText.setText(lastUser);
            passwordEditText.setText(lastPassword);
            roundedImageView.setImageBitmap(bitmap);
//            roundedImageView.setVisibility(roundedImageView.VISIBLE);
//            Log.i("TAG", "user:"+lastUser+",pass:"+lastPassword+",avatar:"+roundedImageView);
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

        usersaved = getDat();
        //创建一个SimpleAdapter适配器
        //第一个参数：上下文，第二个参数：数据源，第三个参数：item子布局，第四、五个参数：键值对，获取item布局中的控件id
        adapter = new SimpleAdapter(this, usersaved, R.layout.useritem, new String[]{"image", "username", "password"}, new int[]{R.id.avatar, R.id.username, R.id.password});
        //控件与适配器绑定
        spinner.setAdapter(adapter);
        spinner.setSelection(0, true);
        //点击事件
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String info=adapterView.getItemAtPosition(i).toString();//获取i所在的文本
                Toast.makeText(LoginActivity.this,info,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    private List<Map<String,Object>> getDat() {
        List<Map<String,Object>> d = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("image", R.drawable.avatar);
        map.put("username", "北京");
        map.put("password", "BeiJing");
        d.add(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("image", R.drawable.avatar);
        map1.put("username", "上海");
        map1.put("password", "ShangHai");
        d.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("image", R.drawable.avatar);
        map2.put("username", "廣州");
        map2.put("password", "GuangZhou");
        d.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("image", R.drawable.avatar);
        map3.put("username", "深圳");
        map3.put("password", "ShenZhen");
        d.add(map3);
        return d;
    }

    public void spinnerBtn(View btn){
        if(spinner.isShown()){
            spinner.performClick();
//            Toast.makeText(getApplicationContext(), "被点了", Toast.LENGTH_LONG).show();
        }
        else {
            spinner.performClick();
//            Toast.makeText(getApplicationContext(), "被点了", Toast.LENGTH_LONG).show();
        }
    }


}