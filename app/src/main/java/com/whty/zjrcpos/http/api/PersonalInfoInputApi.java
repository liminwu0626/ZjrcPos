package com.whty.zjrcpos.http.api;


import com.whty.zjrcpos.entity.PersonalInfoInputEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
* @function PersonalInfoInputApi 个人信息写入
* @author wulimin
* @time 2017/3/14 下午4:53
*/

public interface PersonalInfoInputApi {
    @POST("app")
    Observable<PersonalInfoInputEntity> getInfoInput(@Body RequestBody param);
}
