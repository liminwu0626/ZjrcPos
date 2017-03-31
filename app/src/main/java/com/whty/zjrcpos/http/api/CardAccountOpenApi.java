package com.whty.zjrcpos.http.api;


import com.whty.zjrcpos.entity.CardOpenEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
* @function CardAccountOpenApi 个人借记账号开卡
* @author wulimin
* @time 2017/3/14 下午4:21
*/

public interface CardAccountOpenApi {
//    @GET("img/list.from")
//    Observable<ApiResponseWraper<PersonalInfoInputEntity>> getImageJoke(@QueryMap RequestParam param);
    @POST("app")
    Observable<CardOpenEntity> getCardOpenInfo(@Body RequestBody param);
}
