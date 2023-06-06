package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.UserDBHelper;
import com.example.entity.User;
import com.example.fragment.PublishFragment;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox ckb;

    private UserDBHelper mHelper;
    private Button login;
    private EditText log_username;
    private EditText log_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mHelper = new UserDBHelper(this);
        TextView tv = (TextView) findViewById(R.id.tv1);
        String text = tv.getText().toString();
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        },0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        TextView forget = (TextView) findViewById(R.id.forget);
        String fg = forget.getText().toString();
        SpannableString ss1 = new SpannableString(fg);
        ss1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginActivity.this,LoginForgetActivity.class);
                startActivity(intent);
            }
        },0,text.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forget.setText(ss1);
        forget.setMovementMethod(LinkMovementMethod.getInstance());
        // check box
        login = findViewById(R.id.login);
        log_username = findViewById(R.id.log_username);
        log_password = findViewById(R.id.log_pwd);
        ckb = (CheckBox)findViewById(R.id.log_ckb);
        preferences = getSharedPreferences("config",Context.MODE_PRIVATE);
        boolean isRemember = preferences.getBoolean("remember_password", false);
        if (isRemember){
            String username1 = preferences.getString("username","");
            String password1 = preferences.getString("password","");
            log_username.setText(username1);
            log_password.setText(password1);
            ckb.setChecked(true);
        }
        // Button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = log_username.getText().toString();
                String password = log_password.getText().toString();
                if ("".equals(username)||"".equals(password)){
                    Toast.makeText(LoginActivity.this, "The account and password should not be empty", Toast.LENGTH_SHORT).show();
                }else {
                    List<User> list = mHelper.queryByRegister();
                    boolean result = false;
                    for (int i =0; i< list.size(); i++){
                        User user = list.get(i);
                        if (username.equals(user.getUsername())&&password.equals(user.getPassword())){
                            result = true;
                            break;
                        }else {
                            result = false;
                        }
                    }

                    if (result){
                        editor = preferences.edit();
                        if (ckb.isChecked()){
                            editor.putBoolean("remember_password",true);
                            editor.putString("username",username);
                            editor.putString("password",password);
                        }else {
                            editor.clear();
                        }
                        editor.commit();
                        Toast.makeText(LoginActivity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mHelper = UserDBHelper.getInstance(this);
        mHelper.openReadLink();
    }


}