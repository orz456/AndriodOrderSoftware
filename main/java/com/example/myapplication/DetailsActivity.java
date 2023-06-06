package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String publisher = intent.getStringExtra("publisher");
        TextView pub = findViewById(R.id.tv_publish);
        pub.setText(publisher);
        String title = intent.getStringExtra("title");
        TextView tit = findViewById(R.id.tv_detail_title);
        tit.setText(title);
        String price = intent.getStringExtra("price");
        TextView pri = findViewById(R.id.tv_detail_price);
        pri.setText("￥"+price);
        String startDate= intent.getStringExtra("startDate");
        TextView sDa = findViewById(R.id.st_date);
        sDa.setText(startDate);
        String startTime= intent.getStringExtra("startTime");
        TextView sTi = findViewById(R.id.st_time);
        sTi.setText(startTime);
        String finishDate= intent.getStringExtra("finishDate");
        TextView fDa = findViewById(R.id.f_date);
        fDa.setText(finishDate);
        String finishTime= intent.getStringExtra("finishTime");
        TextView fTi = findViewById(R.id.f_time);
        fTi.setText(finishTime);
        String region= intent.getStringExtra("region");
        TextView reg = findViewById(R.id.tv_detail_region);
        reg.setText(region);
        String content= intent.getStringExtra("content");
        TextView con = findViewById(R.id.tv_content);
        con.setText(content);
    }
}