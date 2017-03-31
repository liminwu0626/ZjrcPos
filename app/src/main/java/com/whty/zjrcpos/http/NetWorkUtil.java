package com.whty.zjrcpos.http;


import com.whty.zjrcpos.http.api.CardAccountOpenApi;
import com.whty.zjrcpos.http.api.PersonalInfoInputApi;
import com.whty.zjrcpos.http.api.TextJokeApi;
import com.whty.zjrcpos.http.api.TimeTextJokeApi;
import com.whty.zjrcpos.http.api.WeatherApi;
import com.whty.zjrcpos.util.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
* @function NetWorkUtil
* @author wulimin
* @time 2017/3/13 上午10:49
*/

public class NetWorkUtil {
    private static OkHttpClient okHttpClient;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static TextJokeApi textJokeApi;
    private static TimeTextJokeApi timeTextJokeApi;
    private static PersonalInfoInputApi sPersonalInfoInputApi;
    private static CardAccountOpenApi sCardAccountOpenApi;
    private static WeatherApi weatherApi ;
    /**
     * 初始化okhttp
     */
    public static void initOkhttp() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        }

    }


    /**
     * 获取最新文本笑话
     *
     * @return
     */
    public static TextJokeApi getTextJokeApi() {
        initOkhttp();
        if (textJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.RANDOM_BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            textJokeApi = retrofit.create(TextJokeApi.class);
        }
        return textJokeApi;
    }
    /**
     * 获取全部按时间文本笑话
     *
     * @return
     */
    public static TimeTextJokeApi getTimeTextJokeApi() {
        initOkhttp();
        if (timeTextJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            timeTextJokeApi = retrofit.create(TimeTextJokeApi.class);
        }
        return timeTextJokeApi;
    }
    /**
     * 个人信息写入
     *
     * @return
     */
    public static PersonalInfoInputApi getPersonalInfoInputApi() {
        initOkhttp();
        if (sPersonalInfoInputApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            sPersonalInfoInputApi = retrofit.create(PersonalInfoInputApi.class);
        }
        return sPersonalInfoInputApi;
    }
    /**
     * 账户开卡
     *
     * @return
     */
    public static CardAccountOpenApi getCardAccountOpenApi() {
        initOkhttp();
        if (sCardAccountOpenApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            sCardAccountOpenApi = retrofit.create(CardAccountOpenApi.class);
        }
        return sCardAccountOpenApi;
    }

    /**
     * 获取天气数据
     * @return
     */
    public static WeatherApi getWeatherApi(){
        initOkhttp();
        if (weatherApi==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_WHEATHER)
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            weatherApi = retrofit.create(WeatherApi.class);
        }
        return weatherApi;
    }



}
