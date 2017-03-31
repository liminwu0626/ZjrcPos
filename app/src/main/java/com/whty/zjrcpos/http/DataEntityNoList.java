package com.whty.zjrcpos.http;

import com.google.gson.annotations.SerializedName;
import com.whty.zjrcpos.entity.BaseEntity;
import com.whty.zjrcpos.entity.WeatherEntity;

/**
 * Created by zhihao.wen on 2016/11/3.
 */

public class DataEntityNoList<T> extends BaseEntity {
    @SerializedName("data")
    private WeatherEntity data ;

    public WeatherEntity getData() {
        return data;
    }

    public void setData(WeatherEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                ", data=" + data +
                '}';
    }
}
