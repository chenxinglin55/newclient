package com.example.administrator.newclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/11/28.
 */

public class NewsDBHelper extends SQLiteOpenHelper{


    public NewsDBHelper(Context context) {
        super(context,"news.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //该表用于保存新闻分类的数据
        db.execSQL("create table news_type(_id integer primary key autoIncrement,subid text,subgroup text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
