package com.whty.zjrcpos.http.api;


import com.whty.zjrcpos.entity.WeatherEntity;
import com.whty.zjrcpos.http.ApiResponseWraperNoList;
import com.whty.zjrcpos.http.RequestParam;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by zhihao.wen on 2016/11/23.
 */

public interface WeatherApi {
    @GET("query")
    Observable<ApiResponseWraperNoList<WeatherEntity>> getWeather(@QueryMap RequestParam param);
}

