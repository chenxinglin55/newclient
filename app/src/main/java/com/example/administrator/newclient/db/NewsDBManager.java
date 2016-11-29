package com.example.administrator.newclient.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.newclient.entity.SubType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */

public class NewsDBManager {

    private NewsDBHelper helper;
    private Context context;

    public NewsDBManager(Context context){
        this.context = context;
        this.helper = new NewsDBHelper(context);

    }
    //获取新闻分类的方法
    public List<SubType> getNewsSubType(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from news_type order by _id desc", null);
        List<SubType> list = new ArrayList<SubType>();

        while(cursor.moveToNext()){
            int subid = cursor.getInt(cursor.getColumnIndex("subid"));
            String subgroup = cursor.getString(cursor.getColumnIndex("subgroup"));
            SubType subType = new SubType(subid, subgroup);
            list.add(subType);
        }

        cursor.close();
        cursor = null;
        return list;

    }

}
