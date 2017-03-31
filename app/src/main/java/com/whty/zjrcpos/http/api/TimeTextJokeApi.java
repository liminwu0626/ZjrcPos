package com.whty.zjrcpos.http.api;


import com.whty.zjrcpos.entity.TextJokeEntity;
import com.whty.zjrcpos.http.ApiResponseWraper;
import com.whty.zjrcpos.http.RequestParam;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by zhihao.wen on 2016/4/20.
 */
public interface TimeTextJokeApi {
    @GET("content/list.from")
    Observable<ApiResponseWraper<TextJokeEntity>> getTextJoke(@QueryMap RequestParam param);
}
