package com.example.administrator.newclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.newclient.R;
import com.example.administrator.newclient.adapter.NewsAdapter;
import com.example.administrator.newclient.adapter.NewsTypeAdapter;
import com.example.administrator.newclient.biz.NewsManager;
import com.example.administrator.newclient.db.NewsDBManager;
import com.example.administrator.newclient.entity.News;
import com.example.administrator.newclient.entity.SubType;
import com.example.administrator.newclient.parser.NewsParser;
import com.example.administrator.newclient.utils.CommonUtils;
import com.example.administrator.newclient.view.HorizontalListView;
import com.example.administrator.newclient.xlistView.XListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/28.
 */

public class MainFragment extends Fragment {

    @BindView(R.id.hl_type)
    HorizontalListView hlType;
    @BindView(R.id.iv_type_more)
    ImageView ivTypeMore;
    @BindView(R.id.hl_listView)
    XListView listView;

    private NewsDBManager newsDBManager;
    private NewsTypeAdapter newsTypeAdapter;

    private NewsAdapter newsAdapter;
    private int subid = 1;//分类编号
    private int refreshMode = 1;//加载数据模式



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        newsTypeAdapter = new NewsTypeAdapter(getActivity());
        newsDBManager = new NewsDBManager(getActivity());
        hlType.setAdapter(newsTypeAdapter);

        loadNewsType();

        newsAdapter = new NewsAdapter(getActivity());
        //启用上拉加载
        listView.setPullLoadEnable(true);
        //启动下拉刷新
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(listViewListener);
        listView.setAdapter(newsAdapter);
        refreshMode = NewsManager.MODE_PULL_REFRESH;
        loadNextNews(true);
        //显示加载进度对话框

        return view;
    }

    private XListView.IXListViewListener listViewListener = new XListView.IXListViewListener() {
        //下拉刷新会回调该方法
        @Override
        public void onRefresh() {
            refreshMode = NewsManager.MODE_PULL_REFRESH;
            loadNextNews(false);
        }

        //上拉加载会回调该方法
        @Override
        public void onLoadMore() {
            refreshMode = NewsManager.MODE_LOAD_MORE;
            loadPrevNews();
        }
    };

    //加载新数据
    private void loadNextNews(boolean isNewType){
        int nid = 1;
        //如果isNewType为true,则是第一次加载数据，此时nid=1,如果不是第一次加载数据，则需要获取列表中第一条数据的新闻编号
        if(!isNewType){
            if(newsAdapter.getData().size() > 0){
                nid = newsAdapter.getData().get(0).getNid();
            }
        }

        if(CommonUtils.isNetConnect(getActivity())){
            NewsManager.getNewsList(getActivity(),subid,refreshMode, nid, newsListener, errorListener);
        }else{
            //从缓存中获取新闻数据
        }
    }

    //加载以前的数据
    private void loadPrevNews(){
        //没有数据，不需要进行上拉加载数据
        if(newsAdapter.getData().size() == 0){
            return;
        }

        int lastIndex = newsAdapter.getData().size() - 1;
        int nid = newsAdapter.getData().get(lastIndex).getNid();
        if(CommonUtils.isNetConnect(getActivity())){
            NewsManager.getNewsList(getActivity(),subid,refreshMode, nid, newsListener, errorListener);
        }else{
            //从缓存中获取新闻数据
        }
    }

    //加载新闻分类
    private void loadNewsType(){
        //先判断数据库中是否有缓存的数据，如果有，则使用缓存的数据，如果没有，则判断是否有网络，有网络，则去服务器端加载数据
        if(newsDBManager.getNewsSubType().size() == 0){
            if(CommonUtils.isNetConnect(getActivity())){
                NewsManager.getNewsType(getActivity(),newsTypeListener, errorListener);
            }
        }else{
            List<SubType> list = newsDBManager.getNewsSubType();
            newsTypeAdapter.appendDataToAdapter(list, true);
            newsTypeAdapter.notifyDataSetChanged();
        }
    }

    //获取新闻分类成功之后回调的接口
    private Response.Listener<String> newsTypeListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String json) {
            List<SubType> list = NewsParser.parseNewsType(json);
            newsTypeAdapter.appendDataToAdapter(list, true);
            newsTypeAdapter.notifyDataSetChanged();
        }
    };

    private Response.Listener<String> newsListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String json) {
            List<News> list = NewsParser.parseNews(json);
            boolean isClear = refreshMode == NewsManager.MODE_PULL_REFRESH ? true : false;
            newsAdapter.appendDataToAdapter(list, isClear);
            newsAdapter.notifyDataSetChanged();
            listView.stopLoadMore();
            listView.stopRefresh();
            listView.setRefreshTime(CommonUtils.getCurrentDate());
        }
    };

    //获取新闻分类失败之后回调的接口
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //取消进度对话框
            listView.stopLoadMore();
            listView.stopRefresh();
            Toast.makeText(getActivity(),"加载数据失败", Toast.LENGTH_SHORT).show();
        }
    };
}
