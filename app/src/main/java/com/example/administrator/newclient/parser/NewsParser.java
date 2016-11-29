package com.example.administrator.newclient.parser;

import com.example.administrator.newclient.entity.BaseEntity;
import com.example.administrator.newclient.entity.News;
import com.example.administrator.newclient.entity.NewsType;
import com.example.administrator.newclient.entity.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by Administrator on 2016/11/28.
 */

public class NewsParser {

    //解析新闻分类的方法
    public static List<SubType> parseNewsType(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntity<List<NewsType>>>(){}.getType();
        BaseEntity<List<NewsType>> baseEntity = gson.fromJson(json, type);
        if(baseEntity != null){
            NewsType newsType = baseEntity.getData().get(0);
            return newsType.getSubgrp();
        }

        return null;
    }

    //解析新闻列表数据
    public static List<News> parseNews(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntity<List<News>>>(){}.getType();
        BaseEntity<List<News>> baseEntity = gson.fromJson(json, type);
        if(baseEntity != null){
            System.out.println("--------------------" + json);
            return baseEntity.getData();
        }

        return null;
    }

}