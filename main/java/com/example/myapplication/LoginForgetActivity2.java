package com.example.myapplication;

import static com.example.myapplication.RegisterActivity.REGEX_PASSWORD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databases.UserDBHelper;
import com.example.entity.User;

import java.util.List;
import java.util.regex.Pattern;

public class LoginForgetActivity2 extends AppCompatActivity {
    private EditText ed_pwd,ed_re_pwd;
    private Button btn_change;
    private UserDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget_2);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String verQ = intent.getStringExtra("verify_Question");
        String verA = intent.getStringExtra("verify_Answer");

        ed_pwd = (EditText) findViewById(R.id.forget_pwd);
        ed_re_pwd = (EditText) findViewById(R.id.forget_re_pwd);
        btn_change = (Button) findViewById(R.id.forget_btn_change);

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = ed_pwd.getText().toString();
                String re_password = ed_re_pwd.getText().toString();
                User user = null;
                if ("".equals(password)||"".equals(re_password)){
                    Toast.makeText(LoginForgetActivity2.this, "The new password and re-enter password should not be empty", Toast.LENGTH_SHORT).show();

                }else if(!password.equals(re_password)){
                    Toast.makeText(LoginForgetActivity2.this,"The password and re-enter password is different",Toast.LENGTH_SHORT).show();
                }else {
                    boolean judge = isPassword(password);
                    if (judge){
                        Toast.makeText(LoginForgetActivity2.this,"Reset password Successfully",Toast.LENGTH_SHORT).show();
                        user = new User(username,password,verQ,verA);
                        mHelper.update(user);
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginForgetActivity2.this,"The password is invalid",Toast.LENGTH_SHORT).show();

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
        mHelper.openWriteLink();
    }

    private boolean isPassword(String pwd) {
        return Pattern.matches(REGEX_PASSWORD,pwd);
    }

}