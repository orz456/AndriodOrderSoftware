package com.example.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db";
    private static final String TABLE_NAME = "user_info";
    private static final int DB_VERSION= 2;
    private static UserDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;



    public UserDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static UserDBHelper getInstance(Context context){
        if (mHelper == null){
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
                " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " username VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL," +
                " verifyQuestion VARCHAR NOT NULL," +
                " verifyAnswer VARCHAR NOT NULL);";

        String sql1 = "CREATE TABLE IF NOT EXISTS "+"publish_info"+" ("+
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
        db.execSQL(sql1);

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

    public void closeLink(){
        if (mRDB !=null && mRDB.isOpen()){
            mRDB.close();
            mRDB=null;
        }
        if (mWDB !=null && mWDB.isOpen()){
            mWDB.close();
            mWDB=null;
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long insert(User user){
        ContentValues values = new ContentValues();
        values.put("username",user.username);
        values.put("password",user.password);
        values.put("verifyQuestion",user.verifyQuestion);
        values.put("verifyAnswer",user.verifyAnswer);
        return mWDB.insert(TABLE_NAME,null,values);
    }

    public long update(User user){
        ContentValues values = new ContentValues();
        values.put("username",user.username);
        values.put("password",user.password);
        values.put("verifyQuestion",user.verifyQuestion);
        values.put("verifyAnswer",user.verifyAnswer);
        return mWDB.update(TABLE_NAME,values,"username=?",new String[]{user.username});
    }



    public List<User> queryByRegister(){
        List<User> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            User user = new User();
            user.id = cursor.getInt(0);
            user.username = cursor.getString(1);
            user.password = cursor.getString(2);
            user.verifyQuestion = cursor.getString(3);
            user.verifyAnswer = cursor.getString(4);
            list.add(user);
        }
        return list;
    }


}
