package com.example.administrator.newclient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.newclient.R;
import com.example.administrator.newclient.entity.SubType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/28.
 */

public class NewsTypeAdapter extends BaseAdapter{
    private List<SubType> list = new ArrayList<SubType>();
    private Context context;
    private LayoutInflater inflater;
    private int position;//保存当前选中的条目

    public NewsTypeAdapter(Context context){
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void appendDataToAdapter(List<SubType> data, boolean isClear){
        if(data == null || data.size() == 0){
            return;
        }

        if(isClear){
            list.clear();
        }

        list.addAll(data);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.newstype_list_item,null);
            holder = new ViewHolder();
            ButterKnife.bind(holder,convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        SubType subType = list.get(i);
        holder.tvNewsType.setText(subType.getSubgroup());

        if(i == position){//当前显示的条目与选中的条目相等，此时显示特殊的颜色
            holder.tvNewsType.setTextColor(Color.RED);
        }else {
            holder.tvNewsType.setTextColor(Color.BLACK);
        }


        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.tv_news_type)
        TextView tvNewsType;
    }
}
