package com.example.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.MyAdapter;
import com.example.databases.PublishDBHelper;
import com.example.entity.Publish;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private static final String[] region = {"All Campus","ChaoYang Campus ","TongZhou Campus","ZhongLan Campus"};
    private static final String[] sort = {"Default Sorting","The highest price","The lowest price"};

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Date curDate = new Date(System.currentTimeMillis());
    String string = format.format(curDate);
    private String username;
    private PublishDBHelper mHelper;
    private View view;
    private TextView tv_renew;
    private Spinner sp_region, sp_sort;
    public RecyclerView mRecyclerView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Publish> publishList = new ArrayList<>();
    private MyAdapter myAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "fragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        Log.d(TAG,"Fragment onAttach");
//    }

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
//        Log.d(TAG,"onCreateView");
        username = getActivity().getIntent().getStringExtra("username");
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initData(0);
        initRecyclerView();
        initSwipeRefreshLayout();
        initView(view);

//        System.out.println(string);
        return view;
    }




    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        publishList.clear();
                        myAdapter.notifyDataSetChanged();
                        initData(0);
                        initRecyclerView();
                    }
                },2000);
            }
        });
    }

//    private void

    private void initData(int position) {
        mHelper = new PublishDBHelper(getContext());
        SQLiteDatabase sqLiteDatabase = mHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("publish_info",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
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
//            String time = finishDate + " " + finishTime;
//            boolean result = false;
//            try {
//                result = dateCompare(string,time);
//                System.out.println(result);
//                System.out.println(string);
//                System.out.println(time);
//                System.out.println(publishList);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            if (result){
//                overTime(id);
//            }

            if (status ==1){
                Publish publish = new Publish(username,title,content,price,startDate,startTime,finishDate,finishTime,region,status,orderNumber,null);
                if (position == 0){
                    publishList.add(publish);
                }else if (position == 1) {
                    if (publish.getRegion().equals("ChaoYang Campus ")){
                        publishList.add(publish);
                    }
                }else if (position == 2){
                    if (publish.getRegion().equals("TongZhou Campus")){
                        publishList.add(publish);
                    }
                }else {
                    if (publish.getRegion().equals("ZhongLan Campus")){
                        publishList.add(publish);
                    }
                }
            }

        }
        cursor.close();

    }

//    private boolean dateCompare(String str1,String str2) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//        Date date1 = sdf.parse(str1);
//        Date date2 = sdf.parse(str2);
//        if (date1.compareTo(date2) >= 0) {
//            return false;
//        } else {
//            return true;
//        }
//
//    }

//    private void overTime(int id){
//        PublishDBHelper mHelper = new PublishDBHelper(getContext());
//        SQLiteDatabase database = mHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("status",2);
//        database.update("publish_info",values,"title=?", new String[]{publishList.get(id).title});
//        publishList.clear();
//
//        myAdapter.notifyDataSetChanged();
//        initData(0);
//        initRecyclerView();
////        myAdapter.notifyItemRemoved(id);
////        myAdapter.notifyItemRangeChanged(id,publishList.size());
//    }

    private void initView(View view) {
        tv_renew = (TextView) view.findViewById(R.id.tv_renew);
        tv_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishList.clear();
                myAdapter.notifyDataSetChanged();
                initData(0);
                initRecyclerView();
            }
        });
        sp_region = (Spinner) view.findViewById(R.id.sp_region);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,region);
        sp_region.setAdapter(adapter);
        sp_region.setVisibility(View.VISIBLE);
        sp_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                publishList.clear();
                myAdapter.notifyDataSetChanged();
                initData(position);
                initRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_sort = (Spinner) view.findViewById(R.id.sp_sort);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,sort);
        sp_sort.setAdapter(adapter1);
        sp_sort.setVisibility(View.VISIBLE);
        sp_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                publishList.clear();
                myAdapter.notifyDataSetChanged();
                initData(0);
                if (position == 2) {
                    Collections.sort(publishList, new Comparator<Publish>() {
                        @Override
                        public int compare(Publish p1, Publish p2) {
                            if (Integer.parseInt(p1.getPrice()) > Integer.parseInt(p2.getPrice())) {
                                return 1;
                            } else if (Integer.parseInt(p1.getPrice()) == Integer.parseInt(p2.getPrice())) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    });
                } else if (position == 1) {
                    Collections.sort(publishList, new Comparator<Publish>() {
                        @Override
                        public int compare(Publish p1, Publish p2) {
                            if (Integer.parseInt(p1.getPrice()) > Integer.parseInt(p2.getPrice())) {
                                return -1;
                            } else if (Integer.parseInt(p1.getPrice()) == Integer.parseInt(p2.getPrice())) {
                                return 0;
                            } else {
                                return 1;
                            }
                        }
                    });
                }
                initRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rev);
        myAdapter = new MyAdapter(publishList,getContext(),username);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(myAdapter);
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

