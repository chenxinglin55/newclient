package com.example.administrator.newclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/28.
 */

public class RightMenuFragment extends Fragment {
  //未登录时的组件视图
    @BindView(R.id.ll_unlogin)
    LinearLayout llUnLogin;
    @BindView(R.id.iv_unlogin)
    ImageView ivUnLogin;
    @BindView(R.id.tv_unlogin)
    TextView tvUnlogin;

    //已登陆时的组件视图
    @BindView(R.id.ll_logined)
    LinearLayout llLogined;
    @BindView(R.id.iv_logined)
    ImageView ivLogined;
    @BindView(R.id.tv_logined)
    TextView tvLogined;

    //第三方登陆的组件视图
    @BindView(R.id.iv_share_weixin)
    ImageView ivWeixin;
    @BindView(R.id.iv_share_qq)
    ImageView ivQQ;
    @BindView(R.id.iv_share_friend)
    ImageView ivFriend;
    @BindView(R.id.iv_share_weibo)
    ImageView ivWeibo;
    @BindView(R.id.tv_update_version)
    TextView tvUpdateVersion;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_menu,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
