package com.borui.weishare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.LocationUtil;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.SPUtil;
import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.vo.ShareCate;
import com.borui.weishare.vo.Shares;
import com.borui.weishare.vo.UserVo;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by borui on 2017/9/25.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.iv_welcome)
    ImageView ivWelcome;

    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initLocation();
        initShareCate();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissWelcome(true);
            }
        },6000);
    }
    private void initLocation(){

        //初始化位置
        LocationUtil.getInstance().startLocation();
    }
    private void initShareCate(){

        Map<String,String> params=new HashMap<>();
        params.put("dictType","MERCHANT_TYPE");
        VolleyUtil.getInstance().doPost(APIAddress.SHARE_CATE,params,new TypeToken<ShareCate>(){}.getType(),"");
    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void doLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.username)), Toast.LENGTH_LONG).show();
            return;
        }
        if (username.length() < 6) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_less, getResources().getString(R.string.username), 6), Toast.LENGTH_LONG).show();
            return;
        }
        if (username.length() > 20) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_more, getResources().getString(R.string.username), 20), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.password)), Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_less, getResources().getString(R.string.password), 6), Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() > 20) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_less, getResources().getString(R.string.password), 20), Toast.LENGTH_LONG).show();
            return;
        }

        showProgress("正在登录，请稍候");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        SPUtil.insertString(this, SPUtil.KEY_USERNAME, username);
        SPUtil.insertString(this, SPUtil.KEY_PASSWORD, password);
        VolleyUtil.getInstance().doPost(APIAddress.LOGIN, params, new TypeToken<UserVo>() {
        }.getType(), "login");
//        VolleyUtil.getInstance().doPost(APIAddress.LOGIN,params,null,new TypeToken<UserVo>(){}.getType(),"login");
    }

    boolean amapInited;
    boolean cateInited;
    private void dismissWelcome(boolean forceDismiss){
        if(ivWelcome.getVisibility()==View.VISIBLE){
            if(forceDismiss||(amapInited&&cateInited)){
                ivWelcome.setVisibility(View.GONE);
                etUsername.setText(SPUtil.getString(this,SPUtil.KEY_USERNAME));
                etPassword.setText(SPUtil.getString(this,SPUtil.KEY_PASSWORD));
            }
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(ShareCate shareCate){
        if(shareCate.getCode().equals("0")){
            for (ShareCate.Dict data:shareCate.getData()){
                Cache.shareCache.put(data.getId(),new ArrayList<Shares.ShareItem>());
            }

            Cache.shareCate=shareCate;
        }
        cateInited=true;
        dismissWelcome(false);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(AMapLocation amapLocation){
        dismissProgress();
        if (amapLocation != null&&amapLocation.getErrorCode() == 0) {
            //解析定位结果
            SPUtil.insertString(this,SPUtil.KEY_LATITUDE,amapLocation.getLatitude()+"");
            SPUtil.insertString(this,SPUtil.KEY_LONGITUDE,amapLocation.getLongitude()+"");
            SPUtil.insertString(this,SPUtil.KEY_CITY,amapLocation.getCity());

        }
        amapInited=true;
        dismissWelcome(false);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(UserVo uservo) {
        if (!uservo.getTag().equals("login")) {
            return;
        }
        dismissProgress();
        if (uservo.getCode().equals("0")) {
            Cache.currenUser = uservo;
            SPUtil.insertBoolean(this, SPUtil.KEY_LOGINED, true);

            Intent intent = new Intent();
            intent.putExtra("checkMenu", getIntent().getIntExtra("checkMenu", 0));
            startActivityForResult(new Intent(this, MainActivity.class), 100);
        } else {
            showDialog("登录失败：" + uservo.getMsg());
            SPUtil.insertBoolean(this, SPUtil.KEY_LOGINED, false);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 4) {
                finish();
            }
        }
    }

    private void autoLogin() {
        if (SPUtil.getBoolean(this, SPUtil.KEY_LOGINED) && Cache.currenUser == null) {
            Log.e("==========", "autoLogin: ");
            Map<String, String> params = new HashMap<>();
            params.put("username", SPUtil.getString(this, SPUtil.KEY_USERNAME));
            params.put("password", SPUtil.getString(this, SPUtil.KEY_PASSWORD));
            Log.e("=========", "autoLogin: " + params.get("username") + "//" + params.get("password"));
            VolleyUtil.getInstance().doPost(APIAddress.LOGIN, params, new TypeToken<UserVo>() {
            }.getType(), "autoLogin");
        }

    }


}
