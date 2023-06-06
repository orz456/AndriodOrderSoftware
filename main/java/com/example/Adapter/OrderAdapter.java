package com.example.Adapter;

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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ReceiverDBHelper mHelper;
    private List<Publish> orderList;
    private Context context;
    private String username;

    public OrderAdapter(List<Publish> orderList,Context context, String username) {
        this.orderList = orderList;
        this.context = context;
        this.username = username;
    }
    public List<Publish> getOrderList(){
        return orderList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item1,parent,false);
        final OrderAdapter.ViewHolder holder = new OrderAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Publish publish = orderList.get(position);
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
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Publish publish = orderList.get(position);
        int status = publish.getStatus();
        holder.tv_title.setText(publish.getTitle());
        holder.tv_price.setText("￥:" + publish.getPrice());
        holder.tv_user.setText("Publisher:" + publish.getUsername());
        holder.tv_startDate.setText(publish.getStartDate());
        holder.tv_startTime.setText(publish.getStartTime());
        holder.tv_endDate.setText(publish.getFinishDate());
        holder.tv_endTime.setText(publish.getFinishTime());
        holder.tv_region.setText(publish.getRegion());
        // Finished
        holder.tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 3){
                    finishOrder(position);
                    Toast.makeText(context,"This order is finished!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"This order has already finished!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Chargeback
        holder.tv_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 3){
                    cancelOrder(position);
                    Toast.makeText(context,"This order is canceled",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"This order has already finished, you cannot cancel it!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void finishOrder(int position){
        PublishDBHelper mHelper = new PublishDBHelper(context);
        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status",4);
        database.update("publish_info",values,"title=?", new String[]{orderList.get(position).title});
        orderList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,orderList.size());
    }

    private void cancelOrder(int position){
        PublishDBHelper mHelper = new PublishDBHelper(context);
        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status",1);
        values.put("receiver", (String) null);
        database.update("publish_info",values,"title=?", new String[]{orderList.get(position).title});
        orderList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,orderList.size());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
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
        TextView tv_btn2;
        TextView tv_btn3;


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
            tv_btn2 = itemView.findViewById(R.id.tv_btn2);
            tv_btn3 = itemView.findViewById(R.id.tv_btn3);
        }
    }

}
