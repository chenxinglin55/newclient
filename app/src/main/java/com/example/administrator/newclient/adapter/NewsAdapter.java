package com.example.administrator.newclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newclient.R;
import com.example.administrator.newclient.entity.News;
import com.example.administrator.newclient.utils.LoadImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/29.
 */

public class NewsAdapter extends BaseAdapter{
    private List<News> list = new ArrayList<News>();
    private Context context;
    private LayoutInflater inflater;
    private LoadImage loadImage;

    public NewsAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.loadImage = new LoadImage(context);
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

    public List<News> getData(){
        return list;
    }

    public void appendDataToAdapter(List<News> data,boolean isClear){
        if(data == null || data.size() == 0){
            return;
        }

        if(isClear){
            list.clear();
        }

        list.addAll(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.news_list_item, null);
            holder = new ViewHolder();
            ButterKnife.bind(holder, view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        News news = list.get(i);
        holder.tvContent.setText(news.getSummary());
        holder.tvDate.setText(news.getStamp());
        holder.tvTitle.setText(news.getTitle());
        loadImage.displayBitmap(news.getIcon(), holder.imageView);

        return view;
    }

    class ViewHolder{
        @BindView(R.id.iv_icon)
        ImageView imageView;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_Date)
        TextView tvDate;
    }
}

