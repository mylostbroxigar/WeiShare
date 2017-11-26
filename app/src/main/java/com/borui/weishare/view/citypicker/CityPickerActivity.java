package com.borui.weishare.view.citypicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.borui.weishare.BaseActivity;
import com.borui.weishare.R;
import com.borui.weishare.net.LocationUtil;
import com.borui.weishare.util.SPUtil;
import com.borui.weishare.view.citypicker.adapter.CityListAdapter;
import com.borui.weishare.view.citypicker.db.CityManager;
import com.borui.weishare.view.citypicker.model.City;
import com.borui.weishare.view.citypicker.utils.StringUtils;
import com.borui.weishare.view.citypicker.view.SideLetterBar;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.MerchantVo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Author Bro0cL on 2016/12/16.
 */
public class CityPickerActivity extends BaseActivity implements View.OnClickListener {
    public static final String KEY_PICKED_CITY = "picked_city";

    private ListView mListView;
    private SideLetterBar mLetterBar;
    private ImageView backBtn;
    private LinearLayout layoutLocation;
    private TextView tvCurrCity;
    private CityListAdapter mCityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_activity_city_list);

        initView();
        loadData();
    }

    private void loadData() {
        showProgress("");
        CityManager.getAllCities(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(List<City> cityList) {
        dismissProgress();
        Log.e("boruiz", "onResult: cityLost="+cityList );
        if(cityList!=null){
        mCityAdapter = new CityListAdapter(this, cityList);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(City city) {
                SPUtil.insertString(CityPickerActivity.this, SPUtil.KEY_LATITUDE, city.getLatitude() + "");
                SPUtil.insertString(CityPickerActivity.this, SPUtil.KEY_LONGITUDE, city.getLongitude() + "");
                SPUtil.insertString(CityPickerActivity.this, SPUtil.KEY_CITY, city.getName());
                setResult(200);
                finish();
            }

        });
            mListView.setAdapter(mCityAdapter);
            mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
                @Override
                public void onLetterChanged(String letter) {
                    int position = mCityAdapter.getLetterPosition(letter);
                    mListView.setSelection(position);
                }
            });
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            //解析定位结果
            SPUtil.insertString(this, SPUtil.KEY_LATITUDE, amapLocation.getLatitude() + "");
            SPUtil.insertString(this, SPUtil.KEY_LONGITUDE, amapLocation.getLongitude() + "");
            SPUtil.insertString(this, SPUtil.KEY_CITY, amapLocation.getCity());
            setResult(200);
            finish();
        } else {
            tvCurrCity.setText("定位失败");
            layoutLocation.setClickable(true);
        }
    }
    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_all_city);

        layoutLocation=(LinearLayout)findViewById(R.id.layout_location);
        tvCurrCity=(TextView)findViewById(R.id.tv_curr_city); 
        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);

        backBtn = (ImageView) findViewById(R.id.iv_back);
        backBtn.setOnClickListener(this);
        layoutLocation.setOnClickListener(this);
    }

//    private void back(City city){
//
//        finish();
//    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.layout_location) {
//            searchBox.setText("");
//            clearBtn.setVisibility(View.GONE);
//            emptyView.setVisibility(View.GONE);
//            mResultListView.setVisibility(View.GONE);
            layoutLocation.setClickable(false);
            tvCurrCity.setText("正在定位...");
            LocationUtil.getInstance().startLocation();
        } else if (i == R.id.iv_back) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
