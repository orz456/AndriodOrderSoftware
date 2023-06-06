package com.example.Adapter;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databases.PublishDBHelper;
import com.example.entity.Publish;
import com.example.myapplication.DetailsActivity;
import com.example.myapplication.R;

import java.util.List;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ReceiverDBHelper mHelper;
    private List<Publish> arrayList;
    private Context context;
    private String username;

    public MyAdapter(List<Publish> arrayList,Context context, String username) {
        this.arrayList = arrayList;
        this.context = context;
        this.username = username;
    }

    public List<Publish> getArrayList(){
        return arrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Publish publish = arrayList.get(position);
                Toast.makeText(v.getContext(), "123"+publish.content, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra("publisher",publish.getUsername());
                intent.putExtra("title",publish.getTitle());
                intent.putExtra("price",publish.getPrice());
                intent.putExtra("startDate",publish.getStartDate());
                intent.putExtra("startTime",publish.getStartTime());
                intent.putExtra("finishDate",publish.getFinishDate());
                intent.putExtra("finishTime",publish.getFinishTime());
                intent.putExtra("region",publish.getRegion());
                intent.putExtra("content",publish.getContent());
                intent.putExtra("orderNumber",publish.getOrderNumber());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Publish publish = arrayList.get(position);
        holder.tv_title.setText(publish.getTitle());
        holder.tv_price.setText("￥:" + publish.getPrice());
        holder.tv_user.setText("Publisher:" + publish.getUsername());
        holder.tv_startDate.setText(publish.getStartDate());
        holder.tv_startTime.setText(publish.getStartTime());
        holder.tv_endDate.setText(publish.getFinishDate());
        holder.tv_endTime.setText(publish.getFinishTime());
        holder.tv_region.setText(publish.getRegion());
        boolean result;
        if (Objects.equals(username, publish.getUsername())){
            result = true;
        }else {
            result = false;
        }
        boolean finalResult = result;
        holder.tv_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalResult){
                    Toast.makeText(context,"This order is published by yourselves. you cannot take it",Toast.LENGTH_SHORT).show();
                }else {
//                    String publisher = publish.getUsername();
//                    String title = publish.getTitle();
//                    String price = publish.getPrice();
//                    String startDate = publish.getStartDate();
//                    String startTime = publish.getStartTime();
//                    String finishDate = publish.getFinishDate();
//                    String finishTime = publish.getFinishTime();
//                    String content = publish.getContent();
//                    String region = publish.getRegion();
//                    String orderNumber = publish.getOrderNumber();
//                    Receiver rec = null;
//
//                    rec = new Receiver(username,publisher,title,content,price,startDate,startTime,finishDate,finishTime,region,1,orderNumber);
//                    mHelper = ReceiverDBHelper.getInstance(v.getContext());
//                    mHelper.openReadLink();
//                    mHelper.openWriteLink();
//                    mHelper.insert(rec);
//                    mHelper.closeLink();
                    takeOrder(position);
                    Toast.makeText(context,"This order is token by you",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void takeOrder(int position) {
        PublishDBHelper mHelper = new PublishDBHelper(context);
        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status",3);
        values.put("receiver",username);
        database.update("publish_info",values,"title=?", new String[]{arrayList.get(position).title});
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,arrayList.size());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tv_user;
        TextView tv_region;
        TextView tv_title;
        TextView tv_startDate;
        TextView tv_startTime;
        TextView tv_endDate;
        TextView tv_endTime;
        TextView tv_price;
        TextView tv_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_user = itemView.findViewById(R.id.tv_user);
            tv_region = itemView.findViewById(R.id.tv_region);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_startDate = itemView.findViewById(R.id.tv_start_date);
            tv_startTime = itemView.findViewById(R.id.tv_start_time);
            tv_endDate = itemView.findViewById(R.id.tv_end_date);
            tv_endTime = itemView.findViewById(R.id.tv_end_time);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_btn = itemView.findViewById(R.id.tv_btn);

        }
    }

}
