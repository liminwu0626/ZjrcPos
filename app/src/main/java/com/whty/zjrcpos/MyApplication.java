package com.whty.zjrcpos;

import android.app.Application;
import android.os.Environment;

import com.centerm.cpay.midsdk.dev.DeviceFactory;
import com.centerm.cpay.midsdk.dev.define.IIcCardDev;
import com.orhanobut.logger.Logger;
import com.whty.zjrcpos.entity.LocalUserDataModel;
import com.whty.zjrcpos.util.BaseUtils;

import java.io.File;

/**
 * Created by wulimin on 2017/3/9.
 */

public class MyApplication extends Application {

    public static DeviceFactory factory;
    public static MyApplication singleton;
    public static IIcCardDev icCardDev;
    public static boolean isInitSucc;
    public static final String FILENAME = "userInfo.json";

    @Override
    public void onCreate() {
        super.onCreate();
        //设置 Logger.init(TAG).logLevel(LogLevel.NONE) 可以设置为不打印日志
        Logger.init("浙农信·云POS");
//        factory = DeviceFactory.getInstance();
//        factory.init(getApplicationContext(), EnumSDKType.CPAY_SDK,mInitCallback);
        if (BaseUtils.readLocalUser(MyApplication.this) == null) {
            initLocalUserData();
        }
        String path = Environment.getExternalStorageDirectory() + "/LKShop/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
    }

    public static MyApplication getApplication() {
        if (singleton == null) {
            synchronized (MyApplication.class) {
                singleton = new MyApplication();
            }
        }
        return singleton;

    }


    DeviceFactory.InitCallback mInitCallback = new DeviceFactory.InitCallback() {
        @Override
        public void onResult(boolean finishFlag) {
            //判断结束标志，如果为true，就可以开始调用相应的设备服务
            Logger.i("获取IC卡设备" + finishFlag);
            if (finishFlag) {
                isInitSucc = true;
                try {
                    //获取IC卡设备
                    icCardDev = factory.getIcCardDev();
                } catch (Exception e) {
                    e.printStackTrace();
                    //捕获异常需要进行处理，如果忽略异常，可能会导致程序异常
                }
            }
        }
    };

    private void initLocalUserData() {
        LocalUserDataModel data = new LocalUserDataModel();
        data.setSignName("null");
        data.setUserImg("null");
        data.setUserName("null");
        data.setUid(0);
        data.setLogin(false);
        BaseUtils.saveLocalUser(MyApplication.this, data);
    }
}

