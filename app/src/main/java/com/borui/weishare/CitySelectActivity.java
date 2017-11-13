package com.borui.weishare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.borui.weishare.net.LocationUtil;
import com.borui.weishare.util.SPUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by borui on 2017/11/13.
 */

public class CitySelectActivity extends BaseActivity {
    @BindView(R.id.btn_location)
    Button btnLocation;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;
    @BindView(R.id.btn_4)
    Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_location)
    public void onViewClicked() {

        showProgress("获取定位");
        //初始化位置
        LocationUtil.getInstance().startLocation();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(AMapLocation amapLocation) {
        dismissProgress();
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            //解析定位结果
            SPUtil.insertString(this, SPUtil.KEY_LATITUDE, amapLocation.getLatitude() + "");
            SPUtil.insertString(this, SPUtil.KEY_LONGITUDE, amapLocation.getLongitude() + "");
            SPUtil.insertString(this, SPUtil.KEY_CITY, amapLocation.getCity());
            setResult(200);
            finish();
        } else {
            showDialog("定位失败");
        }
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4})
    public void onViewClicked(View view) {
        String longitude="";
        String latitude="";
        String city="";
        switch (view.getId()) {
            case R.id.btn_1:
                longitude="116.46";
                latitude="39.92";
                city="北京市";
                break;
            case R.id.btn_2:
                longitude="121.48";
                latitude="31.22";
                city="上海市";
                break;
            case R.id.btn_3:
                longitude="114.31";
                latitude="330.52";
                city="武汉市";
                break;
            case R.id.btn_4:
                longitude="113.23";
                latitude="23.16";
                city="广州市";
                break;
        }

        SPUtil.insertString(this,SPUtil.KEY_LATITUDE,latitude);
        SPUtil.insertString(this,SPUtil.KEY_LONGITUDE,longitude);
        SPUtil.insertString(this,SPUtil.KEY_CITY,city);
        setResult(200);
        finish();
    }
}
