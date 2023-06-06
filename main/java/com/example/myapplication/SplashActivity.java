package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class SplashActivity extends Activity {

    long showTime = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(myRunnable, showTime*1000);
        handler.sendEmptyMessage(1);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                showTime--;
                if (showTime>0){
                    handler.sendEmptyMessageDelayed(1,1000);
                }
            }

        }
    };

    Runnable myRunnable=new Runnable() {
        @Override
        public void run() {
            jumpToMainActivity();
        }
    };

    public void jumpToMainActivity(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void closeSplash(View view){
        Log.e("TAG","closeSplash");
        handler.removeCallbacks(myRunnable);
        jumpToMainActivity();

    }

    @Override
    public void onBackPressed() {
    }

}