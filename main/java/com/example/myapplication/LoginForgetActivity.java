package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databases.UserDBHelper;
import com.example.entity.User;

import java.util.List;


public class LoginForgetActivity extends AppCompatActivity {
    private EditText ed_user, ed_question;
    private Spinner sp_question;
    private Button forget_btn_change;
    private ArrayAdapter<String> adapter;
    private UserDBHelper mHelper;
    private String question;
    private TextView goback;
    private static final String[] questions = {"Who is your favorite idol?","What is your favourite fruit?","When is your birthday?","Who do you love most?"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        mHelper = new UserDBHelper(this);
        ed_user = (EditText) findViewById(R.id.ed_user);
        ed_question = (EditText) findViewById(R.id.ed_question);
        sp_question = (Spinner) findViewById(R.id.sp_question);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,questions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_question.setAdapter(adapter);
        sp_question.setVisibility(View.VISIBLE);
        sp_question.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question= sp_question.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        goback = (TextView)findViewById(R.id.tv_back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginForgetActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        forget_btn_change = (Button) findViewById(R.id.forget_btn_change);
        forget_btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_user.getText().toString();
                String answer = ed_question.getText().toString();
                if ("".equals(username)||"".equals(answer)){
                    Toast.makeText(LoginForgetActivity.this, "The username and answer should not be empty", Toast.LENGTH_SHORT).show();
                }else {
                    List<User> list = mHelper.queryByRegister();
                    boolean result = false;
                    for (int i =0; i< list.size(); i++){
                        User user = list.get(i);
                        if (username.equals(user.getUsername())&&answer.equals(user.getVerifyAnswer())&&question.equals(user.getVerifyQuestion())){
                            result = true;
                            break;
                        }else {
                            result = false;
                        }
                    }
                    if (result){
                        Toast.makeText(LoginForgetActivity.this,"Answer validated successfully! ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginForgetActivity.this,LoginForgetActivity2.class);
                        intent.putExtra("username",username);
                        intent.putExtra("verify_Question",question);
                        intent.putExtra("verify_Answer",answer);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginForgetActivity.this,"Answer validation failed! ", Toast.LENGTH_SHORT).show();

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