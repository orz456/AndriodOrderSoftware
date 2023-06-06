package com.example.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Adapter.OrderAdapter;
import com.example.Adapter.OrderAdapter2;
import com.example.databases.PublishDBHelper;
import com.example.entity.Publish;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {
    private String username;
    private PublishDBHelper mHelper;
    private View view;
    public RecyclerView mRecyclerView;
    private List<Publish> orderList = new ArrayList<>();
    private OrderAdapter2 orderAdapter;
    private TextView tv_renew,userID,pub_all,pub2,pub3,pub4;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        username = getActivity().getIntent().getStringExtra("username");
        view = inflater.inflate(R.layout.fragment_my, container, false);
        initData(0);
        initRecyclerView();
        initView(view);
        return view;
    }

    private void initView(View view) {
        userID = (TextView) view.findViewById(R.id.userID);
        userID.setText(username);
        tv_renew = (TextView) view.findViewById(R.id.tv_renew);
        tv_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.clear();
                orderAdapter.notifyDataSetChanged();
                initData(0);
                initRecyclerView();
            }
        });
        pub_all = (TextView) view.findViewById(R.id.pub_all);
        pub_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.clear();
                orderAdapter.notifyDataSetChanged();
                initData(0);
                initRecyclerView();
            }
        });
        pub2 = (TextView) view.findViewById(R.id.pub2);
        pub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.clear();
                orderAdapter.notifyDataSetChanged();
                initData(1);
                initRecyclerView();
            }
        });
        pub3 = (TextView) view.findViewById(R.id.pub3);
        pub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.clear();
                orderAdapter.notifyDataSetChanged();
                initData(2);
                initRecyclerView();
            }
        });
        pub4 = (TextView) view.findViewById(R.id.pub4);
        pub4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.clear();
                orderAdapter.notifyDataSetChanged();
                initData(3);
                initRecyclerView();
            }
        });
    }

    private void initData(int option) {
        mHelper = new PublishDBHelper(getContext());
        SQLiteDatabase sqLiteDatabase = mHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("publish_info", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
//            int id = cursor.getInt(0);
            String user = cursor.getString(1);
            String title = cursor.getString(2);
            String content = cursor.getString(3);
            String price = cursor.getString(4);
            String startDate = cursor.getString(5);
            String startTime = cursor.getString(6);
            String finishDate = cursor.getString(7);
            String finishTime = cursor.getString(8);
            String region = cursor.getString(9);
            int status = cursor.getInt(10);
            String orderNumber = cursor.getString(11);
            String receiver = cursor.getString(12);
            if (Objects.equals(user, username)){
                Publish publish = new Publish(user, title, content, price, startDate, startTime, finishDate, finishTime, region, status, orderNumber, receiver);
                if (option == 0){
                    orderList.add(publish);
                }else if (option ==1){
                    if (publish.getStatus()==3){
                        orderList.add(publish);
                    }
                }else if (option==2){
                    if (publish.getStatus() == 4){
                        orderList.add(publish);
                    }
                }else {
                    if (publish.getStatus()==5){
                        orderList.add(publish);
                    }
                }
            }

        }
        cursor.close();
    }


    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rec1);
        orderAdapter = new OrderAdapter2(orderList,getContext(),username);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(orderAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        mHelper = PublishDBHelper.getInstance(getContext());
        mHelper.openReadLink();
        mHelper.openWriteLink();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHelper.closeLink();
    }
}