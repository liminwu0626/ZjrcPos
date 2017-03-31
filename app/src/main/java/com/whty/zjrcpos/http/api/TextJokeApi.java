package com.whty.zjrcpos.http.api;


import com.whty.zjrcpos.entity.TextJokeEntity;
import com.whty.zjrcpos.http.ApiResponseWraperNoData;
import com.whty.zjrcpos.http.RequestParam;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by zhihao.wen on 2016/4/20.
 */
public interface TextJokeApi {
//    @Query、@QueryMap：用于Http Get请求传递参数
//    @Field：用于Post方式传递参数,需要在请求接口方法上添加@FormUrlEncoded,即以表单的方式传递参数
//    @Body：用于Post,根据转换方式将实例对象转化为对应字符串传递参数.比如Retrofit添加GsonConverterFactory则是将body转化为gson字符串进行传递
//    @Path：用于URL上占位符
//    @Part：配合@Multipart使用,一般用于文件上传
//    @Header：添加http header
//    @Headers：跟@Header作用一样,只是使用方式不一样,@Header是作为请求方法的参数传入,@Headers是以固定方式直接添加到请求方法上
    @GET("randJoke.php")
    Observable<ApiResponseWraperNoData<TextJokeEntity>> getTextJoke(@QueryMap RequestParam param);
}
