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
//        initAmap();
        myApp=this;
    }



    private void initVolley(){
        VolleyUtil.getInstance().init(this);
    }

    private void initJpush(){

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }
//    private void initAmap(){
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getApplicationContext());
//        mLocationListener = new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation amapLocation) {
//                Log.e("borui", "onLocationChanged: "+ amapLocation);
//                if (amapLocation != null) {
//                    if (amapLocation.getErrorCode() == 0) {
//                        Log.e("borui", "onLocationChanged: init" );
//                        //解析定位结果
//                        MyApplication.this.amapLocation=amapLocation;
//                    }
//                }
//            }
//        };
//        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);
//        mLocationOption = new AMapLocationClientOption();
//        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        mLocationOption.setOnceLocation(true);
//        mLocationOption.setNeedAddress(true);
//        //启动定位
//        mLocationClient.setLocationOption(mLocationOption);
//        mLocationClient.startLocation();
//
//    }

}
