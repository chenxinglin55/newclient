package com.example.administrator.newclient.entity;

/**
 * Created by Administrator on 2016/11/28.
 */

public class SubType {
    //子分类编号
    private int stuid;
    //子分类名
    private String subgroup;

    public int getStuid() {
        return stuid;
    }

    public void setStuid(int stuid) {
        this.stuid = stuid;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public SubType(int stuid, String subgroup) {
        this.stuid = stuid;
        this.subgroup = subgroup;
    }

    public SubType() {
    }
}
