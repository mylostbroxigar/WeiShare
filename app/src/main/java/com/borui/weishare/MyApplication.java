package com.borui.weishare;

import android.app.Application;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.borui.weishare.net.VolleyUtil;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by borui on 2017/6/29.
 */

public class MyApplication extends Application {

    public static MyApplication myApp;
    @Override
    public void onCreate() {
        super.onCreate();

        initVolley();
        initJpush();
        myApp=this;
    }



    private void initVolley(){
        VolleyUtil.getInstance().init(this);
    }

    private void initJpush(){

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }

}
