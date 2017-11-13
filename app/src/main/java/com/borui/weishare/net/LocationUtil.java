package com.borui.weishare.net;

import android.net.LocalSocket;
import android.util.EventLog;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.borui.weishare.MyApplication;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by borui on 2017/11/13.
 */

public class LocationUtil {

    //声明AMapLocationClient类对象
    private  AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    private  AMapLocationListener mLocationListener = null;
    //声明AMapLocationClientOption对象
    private  AMapLocationClientOption mLocationOption = null;

    private static LocationUtil locationUtil;
    private LocationUtil(){
        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.myApp);
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
//                Log.e("borui", "onLocationChanged: "+ amapLocation);
                EventBus.getDefault().post(amapLocation);
//                if (amapLocation != null) {
//                    if (amapLocation.getErrorCode() == 0) {
//                        Log.e("borui", "onLocationChanged: init" );
//                        //解析定位结果
//                        MyApplication.this.amapLocation=amapLocation;
//                    }
//                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setNeedAddress(true);
        //启动定位
        mLocationClient.setLocationOption(mLocationOption);
    }

    public static LocationUtil getInstance(){
        if(locationUtil==null)
            locationUtil=new LocationUtil();
        return locationUtil;
    }
    public void startLocation(){
        mLocationClient.startLocation();
    }
}
