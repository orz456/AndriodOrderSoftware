package com.example.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.entity.Publish;
import com.example.entity.User;

import java.util.ArrayList;
import java.util.List;

public class PublishDBHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "publish_info";
    private static final int DB_VERSION= 2 ;
    private static PublishDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public PublishDBHelper(Context context) {
        super(context, "user.db", null, DB_VERSION);
    }

    public static PublishDBHelper getInstance(Context context){
        if (mHelper == null){
            mHelper = new PublishDBHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+"publish_info"+" ("+
                " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " username VARCHAR NOT NULL," +
                " title VARCHAR NOT NULL," +
                " content VARCHAR NOT NULL," +
                " price VARCHAR NOT NULL," +
                " startDate VARCHAR NOT NULL," +
                " startTime VARCHAR NOT NULL," +
                " finishDate VARCHAR NOT NULL," +
                " finishTime VARCHAR NOT NULL," +
                " region VARCHAR NOT NULL," +
                " status VARCHAR NOT NULL," +
                " orderNumber VARCHAR NOT NULL," +
                " receiver VARCHAR);";
        db.execSQL(sql);
    }

    public SQLiteDatabase openReadLink() {
        if (mRDB==null || !mRDB.isOpen()){
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mWDB==null || !mWDB.isOpen()){
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }
        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }
    public long insert(Publish publish){
        ContentValues values = new ContentValues();
        values.put("username",publish.username);
        values.put("title",publish.title);
        values.put("content",publish.content);
        values.put("price",publish.price);
        values.put("startDate",publish.startDate);
        values.put("startTime",publish.startTime);
        values.put("finishDate",publish.finishDate);
        values.put("finishTime",publish.finishTime);
        values.put("region",publish.region);
        values.put("status",publish.status);
        values.put("orderNumber",publish.orderNumber);
        values.put("receiver",publish.receiver);
        return mWDB.insert("publish_info",null,values);
    }


    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long update(Publish publish){
        ContentValues values = new ContentValues();
        values.put("username",publish.username);
        values.put("title",publish.title);
        values.put("title",publish.title);
        values.put("price",publish.price);
        values.put("startDate",publish.startDate);
        values.put("startTime",publish.startTime);
        values.put("finishDate",publish.finishDate);
        values.put("finishTime",publish.finishTime);
        values.put("region",publish.region);
        values.put("status",publish.status);
        values.put("orderNumber",publish.orderNumber);
        values.put("receiver",publish.receiver);
        return mWDB.update(TABLE_NAME,values,"username=?",new String[]{publish.username});
    }

    public List<Publish> query(){
        List<Publish> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Publish publish = new Publish();
            if (publish.getStatus()==1){
                String username = cursor.getString(1);
                String title = cursor.getString(2);
                String content = cursor.getString(3);
                String price = cursor.getString(4);
                String startDate = cursor.getString(5);
                String startTime = cursor.getString(6);
                String finishDate = cursor.getString(7);
                String finishTime = cursor.getString(8);
                String region = cursor.getString(9);
                String orderNumber = cursor.getString(11);
                String receiver = cursor.getString(12);
                list.add(publish);
            }
        }
        return list;
    }
}
