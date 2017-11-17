package com.borui.weishare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.borui.weishare.fragment.AccountFragment;
import com.borui.weishare.fragment.MainFragment;
import com.borui.weishare.fragment.MerchantFragment;
import com.borui.weishare.fragment.MineFragment;
import com.borui.weishare.fragment.ShareEntraFragment;
import com.borui.weishare.jpush.TagAliasOperatorHelper;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.LocationUtil;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.DensityUtil;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    @BindView(R.id.tv_menu_main)
    TextView tvMenuMain;
    @BindView(R.id.layout_menu_main)
    LinearLayout layoutMenuMain;
    @BindView(R.id.tv_menu_share)
    TextView tvMenuShare;
    @BindView(R.id.layout_menu_share)
    LinearLayout layoutMenuShare;
    @BindView(R.id.tv_menu_account)
    TextView tvMenuAccount;
    @BindView(R.id.layout_menu_account)
    LinearLayout layoutMenuAccount;
    @BindView(R.id.tv_menu_mine)
    TextView tvMenuMine;
    @BindView(R.id.layout_menu_mine)
    LinearLayout layoutMenuMine;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;

//    public static final int REQUEST_LOGIN = 0x101;
    @BindView(R.id.tv_menu_merchant)
    TextView tvMenuMerchant;
    @BindView(R.id.layout_menu_merchant)
    LinearLayout layoutMenuMerchant;


    public static int REQUEST_CODE_LOCATION=0x101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        DensityUtil.screenWidth = dm.widthPixels;
        DensityUtil.screenHeight = dm.heightPixels;
        DensityUtil.dpi = dm.density;

        layoutMenuMain.setOnClickListener(this);
        layoutMenuShare.setOnClickListener(this);
        layoutMenuAccount.setOnClickListener(this);
        layoutMenuMine.setOnClickListener(this);
        layoutMenuMerchant.setOnClickListener(this);
        fragments = new Fragment[4];

        if(Cache.currenUser.getData().getRoles().equals(RegisterActivity.ROLE_USER)){
            layoutMenuShare.setVisibility(View.VISIBLE);
            layoutMenuMerchant.setVisibility(View.GONE);
        }else{
            layoutMenuShare.setVisibility(View.GONE);
            layoutMenuMerchant.setVisibility(View.VISIBLE);
        }

        showProgress("获取定位");
        //初始化位置
        LocationUtil.getInstance().startLocation();
//        JPushInterface.setAlias(this,0,"weishare_alias_"+Cache.currenUser.getData().getId());
//        Set<String> tags=new HashSet<>();
//        tags.add("weishare_tag_"+Cache.currenUser.getData().getRoles());
//        JPushInterface.setTags(this,1,tags);
        setTagAlias();
    }

    private void setTagAlias(){
        TagAliasOperatorHelper.TagAliasBean tagBean=new TagAliasOperatorHelper.TagAliasBean();
        tagBean.action=TagAliasOperatorHelper.ACTION_SET;
        tagBean.isAliasAction=false;
        tagBean.tags=new HashSet<>();
        tagBean.tags.add("weishare_tag_"+Cache.currenUser.getData().getRoles());
        TagAliasOperatorHelper.getInstance().handleAction(this,tagBean);


        TagAliasOperatorHelper.TagAliasBean aliasBean=new TagAliasOperatorHelper.TagAliasBean();
        aliasBean.action=TagAliasOperatorHelper.ACTION_SET;
        aliasBean.isAliasAction=true;
        aliasBean.alias="weishare_alias_"+Cache.currenUser.getData().getId();
        TagAliasOperatorHelper.getInstance().handleAction(this,aliasBean);
    }
    private void loadShareCate(){

        showProgress("加载分类");
        Map<String,String> params=new HashMap<>();
        params.put("dictType","MERCHANT_TYPE");
        VolleyUtil.getInstance().doPost(APIAddress.SHARE_CATE,params,new TypeToken<ShareCate>(){}.getType(),"");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(ShareCate shareCate){
        dismissProgress();
        if(shareCate.getCode().equals("0")){
            for (ShareCate.Dict data:shareCate.getData()){
                Cache.shareCache.put(data.getId(),new ArrayList<Shares.ShareItem>());
            }

            Cache.shareCate=shareCate;
            checkMenu(0);
        }else{
            commonDialog=new CommonDialog(this);
            commonDialog.setContent("分类加载失败，点击重试").removeCancleButton().setOKButton(null, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialog.dismiss();
                    loadShareCate();
                }
            }).show();
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(AMapLocation amapLocation){
        dismissProgress();
        if (amapLocation != null&&amapLocation.getErrorCode() == 0) {
            //解析定位结果
            SPUtil.insertString(this,SPUtil.KEY_LATITUDE,amapLocation.getLatitude()+"");
            SPUtil.insertString(this,SPUtil.KEY_LONGITUDE,amapLocation.getLongitude()+"");
            SPUtil.insertString(this,SPUtil.KEY_CITY,amapLocation.getCity());
            onLocationSuccess();
        }else{
            if(SPUtil.getString(this,SPUtil.KEY_LATITUDE).equals("")||
                    SPUtil.getString(this,SPUtil.KEY_LONGITUDE).equals("")||
                    SPUtil.getString(this,SPUtil.KEY_CITY).equals("")){
                startActivityForResult(new Intent(this,CitySelectActivity.class),REQUEST_CODE_LOCATION);
            }
        }
    }

    private void onLocationSuccess(){
        tvCity.setText(SPUtil.getString(this,SPUtil.KEY_CITY));
        //初始化分类
        loadShareCate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_menu_main:
                checkMenu(0);
                break;
            case R.id.layout_menu_merchant:
            case R.id.layout_menu_share:
                checkMenu(1);
                break;
            case R.id.layout_menu_account:
                checkMenu(2);
                break;
            case R.id.layout_menu_mine:
                checkMenu(3);
                break;
        }
    }

    private int currentMenu = -1;
    private Fragment[] fragments;

    public void checkMenu(int i) {
        if (i == currentMenu)
            return;
        layoutMenuMain.setBackgroundColor(ContextCompat.getColor(this, i == 0 ? R.color.button_gray : R.color.button_white));
        layoutMenuShare.setBackgroundColor(ContextCompat.getColor(this, i == 1 ? R.color.button_gray : R.color.button_white));
        layoutMenuMerchant.setBackgroundColor(ContextCompat.getColor(this, i == 1 ? R.color.button_gray : R.color.button_white));
        layoutMenuAccount.setBackgroundColor(ContextCompat.getColor(this, i == 2 ? R.color.button_gray : R.color.button_white));
        layoutMenuMine.setBackgroundColor(ContextCompat.getColor(this, i == 3 ? R.color.button_gray : R.color.button_white));
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        if (fragments[i] == null) {
            switch (i) {
                case 0:
                    fragments[i] = new MainFragment();
                    break;
                case 1:
                    if(Cache.currenUser.getData().getRoles().equals(RegisterActivity.ROLE_USER)){
                        fragments[i] = new ShareEntraFragment();
                    }else{
                        fragments[i] = new MerchantFragment();
                    }

                    break;
                case 2:
                    fragments[i] = new AccountFragment();
                    break;
                case 3:
                    fragments[i] = new MineFragment();
                    break;
            }
            trans.add(R.id.layout_content, fragments[i]);
        }

        hideFragments(trans);
        trans.show(fragments[i]);
        trans.commit();
    }

    private void hideFragments(FragmentTransaction trans) {

        for (Fragment fragment : fragments) {
            if (fragment != null)
                trans.hide(fragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_LOGIN) {
//            if (resultCode == 0) {
//                int checkMenu = data.getIntExtra("checkMenu", 0);
//                checkMenu(checkMenu);
//            }
//        }
        if(requestCode==REQUEST_CODE_LOCATION&&resultCode==200){
            onLocationSuccess();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(4);
        finish();
    }
}
