package com.borui.weishare;

import android.app.Application;

import com.borui.weishare.net.VolleyUtil;


/**
 * Created by borui on 2017/6/29.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        VolleyUtil.getInstance().init(this);
        super.onCreate();
    }


}
