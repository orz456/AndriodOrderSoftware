package com.example.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.databases.PublishDBHelper;
import com.example.entity.Publish;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishFragment extends Fragment implements View.OnClickListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {
    private static final String[] region = {"ChaoYang Campus ","TongZhou Campus","ZhongLan Campus"};
    private View view;
    private String str;
    private Context context;
    private LinearLayout llDate, llTime,llDate1,llTime1;
    private Spinner sp_location;
    private TextView tvDate, tvTime, tvDate1, tvTime1, tvSend;
    private int year, month, day, hour, minute;
    private StringBuffer date, time;
    private PublishDBHelper mHelper;
    private EditText title, content, money;
    private String user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PublishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublishFragment newInstance(String param1, String param2) {
        PublishFragment fragment = new PublishFragment();
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

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
    }

    private void initView(View view) {
        sp_location = (Spinner) view.findViewById(R.id.sp_location);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,region);
        sp_location.setAdapter(adapter);
        sp_location.setVisibility(View.VISIBLE);
        sp_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str = sp_location.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        llDate = (LinearLayout) view.findViewById(R.id.ll_date);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        llTime = (LinearLayout) view.findViewById(R.id.ll_time);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        llDate1 = (LinearLayout) view.findViewById(R.id.ll_date1);
        tvDate1 = (TextView) view.findViewById(R.id.tv_date1);
        llTime1 = (LinearLayout) view.findViewById(R.id.ll_time1);
        tvTime1 = (TextView) view.findViewById(R.id.tv_time1);
        tvSend = (TextView) view.findViewById(R.id.tv_send);
        title = (EditText) view.findViewById(R.id.ed_title);
        content = (EditText) view.findViewById(R.id.ed_content);
        money = (EditText) view.findViewById(R.id.ed_money);

        llDate.setOnClickListener(this);
        llTime.setOnClickListener(this);
        llDate1.setOnClickListener(this);
        llTime1.setOnClickListener(this);
        tvSend.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = getActivity().getIntent().getStringExtra("username");
        view = inflater.inflate(R.layout.fragment_publish, container, false);
        context = getContext();
        date = new StringBuffer();
        time = new StringBuffer();
        initView(view);
        initDateTime();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) {
                            date.delete(0, date.length());
                        }
                        if (month<10&&day<10){
                            tvDate.setText(date.append(String.valueOf(year)).append("/").append("0").append(String.valueOf(month)).append("/").append("0").append(day).append(""));
                        }else if (month>=10&&day<10){
                            tvDate.setText(date.append(String.valueOf(year)).append("/").append(String.valueOf(month)).append("/").append("0").append(day).append(""));
                        }else if (month<10&&day>=10){
                            tvDate.setText(date.append(String.valueOf(year)).append("/").append("0").append(String.valueOf(month)).append("/").append(day).append(""));
                        }else {
                            tvDate.setText(date.append(String.valueOf(year)).append("/").append(String.valueOf(month)).append("/").append(day).append(""));

                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.dp);
                dialog.setTitle("Set Date");
                dialog.setView(dialogView);
                dialog.show();
                datePicker.init(year, month - 1, day, this);
                break;
            case R.id.ll_date1:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
                builder3.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) {
                            date.delete(0, date.length());
                        }
                        if (month<10&&day<10){
                            tvDate1.setText(date.append(String.valueOf(year)).append("/").append("0").append(String.valueOf(month)).append("/").append("0").append(day).append(""));
                        }else if (month>=10&&day<10){
                            tvDate1.setText(date.append(String.valueOf(year)).append("/").append(String.valueOf(month)).append("/").append("0").append(day).append(""));
                        }else if (month<10&&day>=10){
                            tvDate1.setText(date.append(String.valueOf(year)).append("/").append("0").append(String.valueOf(month)).append("/").append(day).append(""));
                        }else {
                            tvDate1.setText(date.append(String.valueOf(year)).append("/").append(String.valueOf(month)).append("/").append(day).append(""));

                        }
                        dialog.dismiss();
                    }
                });
                builder3.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog3 = builder3.create();
                View dialogView3 = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker3 = (DatePicker) dialogView3.findViewById(R.id.dp);

                dialog3.setTitle("Set Date");
                dialog3.setView(dialogView3);
                dialog3.show();
                datePicker3.init(year, month - 1, day, this);
                break;
            case R.id.ll_time:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (time.length() > 0) {
                            time.delete(0, time.length());
                        }
                        if (hour<10&&minute<10){
                            tvTime.setText(time.append("0").append(String.valueOf(hour)).append(":").append("0").append(String.valueOf(minute)).append(""));
                        }else if (hour>=10&&minute<10){
                            tvTime.setText(time.append(String.valueOf(hour)).append(":").append("0").append(String.valueOf(minute)).append(""));
                        }else if (hour<10&&minute>=10){
                            tvTime.setText(time.append("0").append(String.valueOf(hour)).append(":").append(String.valueOf(minute)).append(""));
                        }else {
                            tvTime.setText(time.append(String.valueOf(hour)).append(":").append(String.valueOf(minute)).append(""));
                        }
                        dialog.dismiss();
                    }
                });
                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog2 = builder2.create();
                View dialogView2 = View.inflate(context, R.layout.dialog_time, null);
                TimePicker timePicker = (TimePicker) dialogView2.findViewById(R.id.timePicker);
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
                timePicker.setIs24HourView(true);
                timePicker.setOnTimeChangedListener(this);
                dialog2.setTitle("Setting time");
                dialog2.setView(dialogView2);
                dialog2.show();
                break;
            case R.id.ll_time1:
                AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                builder4.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (time.length() > 0) {
                            time.delete(0, time.length());
                        }
                        if (hour<10&&minute<10){
                            tvTime1.setText(time.append("0").append(String.valueOf(hour)).append(":").append("0").append(String.valueOf(minute)).append(""));
                        }else if (hour>=10&&minute<10){
                            tvTime1.setText(time.append(String.valueOf(hour)).append(":").append("0").append(String.valueOf(minute)).append(""));
                        }else if (hour<10&&minute>=10){
                            tvTime1.setText(time.append("0").append(String.valueOf(hour)).append(":").append(String.valueOf(minute)).append(""));
                        }else {
                            tvTime1.setText(time.append(String.valueOf(hour)).append(":").append(String.valueOf(minute)).append(""));
                        }

                        dialog.dismiss();
                    }
                });
                builder4.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog4 = builder4.create();
                View dialogView4 = View.inflate(context, R.layout.dialog_time, null);
                TimePicker timePicker2 = (TimePicker) dialogView4.findViewById(R.id.timePicker);
                timePicker2.setCurrentHour(hour);
                timePicker2.setCurrentMinute(minute);
                timePicker2.setIs24HourView(true);
                timePicker2.setOnTimeChangedListener(this);
                dialog4.setTitle("Setting time");
                dialog4.setView(dialogView4);
                dialog4.show();
                break;
            case R.id.tv_send:
                String tit = title.getText().toString();
                String ctt = content.getText().toString();
                String mon = money.getText().toString();
                String st_date = tvDate.getText().toString();
                String end_date = tvDate1.getText().toString();
                String st_time = tvTime.getText().toString();
                String end_time = tvTime1.getText().toString();
                boolean result = false;
                try {
                    result = dateCompare(st_date, end_date, st_time, end_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (tit.isEmpty() || ctt.isEmpty() || mon.isEmpty() || st_date.isEmpty() || end_date.isEmpty() || st_time.isEmpty() || end_time.isEmpty()) {
                    Toast.makeText(context, "The data should not be empty!", Toast.LENGTH_SHORT).show();
                } else if (!result) {
                    Toast.makeText(context, "The start time should be earlier than the end time!", Toast.LENGTH_SHORT).show();
                } else {
                    title.setText("");
                    content.setText("");
                    money.setText("");
                    tvDate.setText("");
                    tvDate1.setText("");
                    tvTime.setText("");
                    tvTime1.setText("");
                    Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                    Publish publish = null;
                    publish = new Publish(user, tit, ctt, mon, st_date, st_time, end_date, end_time, str, 1,tit+st_date+user,null);
                    mHelper.insert(publish);
                }


        }
    }
    private boolean dateCompare(String str1,String str2,String str3, String str4) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date1 = sdf.parse(str1+" "+str3);
        Date date2 = sdf.parse(str2+" "+str4);
        if (date1.compareTo(date2) >= 0) {
            return false;
        } else {
            return true;
        }

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

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }
}