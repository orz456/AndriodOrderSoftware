package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.entity.User;
import com.example.databases.UserDBHelper;

import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText pwd_1;
    private EditText pwd_2;
    private EditText verify;
    private Spinner sp;
    private String str;
    private EditText uid_1;
    private ArrayAdapter<String> adapter;
    private Button btn_register;
    private static final String[] questions = {"Who is your favorite idol?","What is your favourite fruit?","When is your birthday?","Who do you love most?"};
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    private UserDBHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        ButterKnife.bind(this);

        // Link
        TextView tv = (TextView) findViewById(R.id.tv2);
        String text = tv.getText().toString();
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        },0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        //Button
        mHelper= new UserDBHelper(this);
        btn_register = findViewById(R.id.btn_register);
        pwd_1 = findViewById(R.id.pwd_1);
        pwd_2 = findViewById(R.id.re_pwd);
        uid_1 = findViewById(R.id.uid_1);
        verify = findViewById(R.id.ed_register_question);
        sp = findViewById(R.id.sp_register_question);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,questions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setVisibility(View.VISIBLE);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str = sp.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // The judge of the invalid of the username and password
                String uid = uid_1.getText().toString();
                String pwd = pwd_1.getText().toString();
                String re_pwd = pwd_2.getText().toString();
                String ver = verify.getText().toString();
                User user= null;
                if ("".equals(uid) || "".equals(pwd) || "".equals(re_pwd)||"".equals(ver)) {
                    Toast.makeText(RegisterActivity.this, "The account, answer, password and re_enter password should not be empty", Toast.LENGTH_SHORT).show();
                }else if (!pwd.equals(re_pwd)){
                    Toast.makeText(RegisterActivity.this,"The password and re-enter password is different",Toast.LENGTH_SHORT).show();

                }else{
                    String uid1 = uid_1.getText().toString();
                    boolean judge = isMobile(uid1);
                    String pwd1 = pwd_1.getText().toString();
                    boolean judge1 = isPassword(pwd1);
                    String pwd2 = pwd_2.getText().toString();
                    boolean judge2 = isPassword(pwd2);
                    boolean result = false;
                    List<User> list = mHelper.queryByRegister();
                    for (int i = 0; i< list.size();i++){
                        User user_register = list.get(i);
                        if (uid1.equals(user_register.getUsername())){
                            result = true;
                            break;
                        }else {
                            result = false;
                        }
                    }
                    if (result == true){
                        Toast.makeText(RegisterActivity.this,"The username is already registered",Toast.LENGTH_SHORT).show();
                    }else {
                        if (judge && judge1 && judge2){
                            Toast.makeText(RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
//                        sdb.execSQL("insert into user_mo(uid,pwd)values('"+uid+"','"+pwd+"')");
                            user = new User(uid,pwd1,str,ver);
                            mHelper.insert(user);
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,"The username or password is invalid",Toast.LENGTH_SHORT).show();
                        }
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

    private boolean isMobile(String uid) {
        String num = "[1][3589]\\d{9}";
        if (TextUtils.isEmpty(uid)){
            return false;
        }else {
            return uid.matches(num);
        }
    }


}