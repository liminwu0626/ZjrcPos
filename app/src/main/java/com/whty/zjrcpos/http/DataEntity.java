package com.whty.zjrcpos.http;

import com.google.gson.annotations.SerializedName;
import com.whty.zjrcpos.entity.BaseEntity;

import java.util.List;

/**
* @function DataEntity 回调信息统一封装类
* @author wulimin
* @time 2017/3/13 下午3:14
*/


public class DataEntity<T> extends BaseEntity {
    @SerializedName("stat")
    private String stat ;
    @SerializedName("data")
    private List<T>  data ;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "stat='" + stat + '\'' +
                ", data=" + data +
                '}';
    }
}
