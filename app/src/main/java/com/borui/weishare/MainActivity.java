package com.borui.weishare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.borui.weishare.fragment.AccountFragment;
import com.borui.weishare.fragment.MainFragment;
import com.borui.weishare.fragment.MerchantFragment;
import com.borui.weishare.jpush.TagAliasOperatorHelper;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.DensityUtil;
import com.borui.weishare.util.SPUtil;
import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.view.CommonInputDialog;
import com.borui.weishare.vo.MerchantVo;
import com.borui.weishare.vo.ShareCate;
import com.borui.weishare.vo.Shares;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{


    public static int REQUEST_CODE_LOCATION = 0x101;
    public static int REQUEST_CODE_MINE = 0x102;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.btn_mine)
    Button btnMine;
    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    @BindView(R.id.iv_menu_main)
    ImageView ivMenuMain;
    @BindView(R.id.tv_menu_main)
    TextView tvMenuMain;
    @BindView(R.id.layout_menu_main)
    LinearLayout layoutMenuMain;
    @BindView(R.id.iv_menu_account)
    ImageView ivMenuAccount;
    @BindView(R.id.tv_menu_account)
    TextView tvMenuAccount;
    @BindView(R.id.layout_menu_account)
    LinearLayout layoutMenuAccount;
    @BindView(R.id.iv_menu_share)
    ImageView ivMenuShare;
    @BindView(R.id.tv_menu_share)
    TextView tvMenuShare;
    @BindView(R.id.layout_menu_share)
    LinearLayout layoutMenuShare;
    @BindView(R.id.iv_menu_merchant)
    ImageView ivMenuMerchant;
    @BindView(R.id.tv_menu_merchant)
    TextView tvMenuMerchant;
    @BindView(R.id.layout_menu_merchant)
    LinearLayout layoutMenuMerchant;
    @BindView(R.id.btn_weishare)
    ImageView btnWeishare;
    @BindView(R.id.btn_localshare)
    ImageView btnLocalshare;
    @BindView(R.id.layout_share)
    RelativeLayout layoutShare;

    CommonInputDialog inputDialog;

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

//        layoutMenuMain.setOnClickListener(this);
//        layoutMenuShare.setOnClickListener(this);
//        layoutMenuAccount.setOnClickListener(this);
//        layoutMenuMine.setOnClickListener(this);
//        layoutMenuMerchant.setOnClickListener(this);
        fragments = new Fragment[3];

        if (Cache.currenUser.getData().getRoles().equals(RegisterActivity.ROLE_USER)) {
            layoutMenuShare.setVisibility(View.VISIBLE);
            layoutMenuMerchant.setVisibility(View.GONE);
            layoutShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleShareLayout();
                }
            });
        } else {
            layoutMenuShare.setVisibility(View.GONE);
            layoutMenuMerchant.setVisibility(View.VISIBLE);
        }

        setTagAlias();

        if (SPUtil.getString(this, SPUtil.KEY_LATITUDE).equals("") ||
                SPUtil.getString(this, SPUtil.KEY_LONGITUDE).equals("") ||
                SPUtil.getString(this, SPUtil.KEY_CITY).equals("")) {
            startActivityForResult(new Intent(this, CitySelectActivity.class), REQUEST_CODE_LOCATION);
        } else {
            onLocationSuccess();
        }
    }

    private void setTagAlias() {
        TagAliasOperatorHelper.TagAliasBean tagBean = new TagAliasOperatorHelper.TagAliasBean();
        tagBean.action = TagAliasOperatorHelper.ACTION_SET;
        tagBean.isAliasAction = false;
        tagBean.tags = new HashSet<>();
        tagBean.tags.add("weishare_tag_" + Cache.currenUser.getData().getRoles());
        TagAliasOperatorHelper.getInstance().handleAction(this, tagBean);


        TagAliasOperatorHelper.TagAliasBean aliasBean = new TagAliasOperatorHelper.TagAliasBean();
        aliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        aliasBean.isAliasAction = true;
        aliasBean.alias = "weishare_alias_" + Cache.currenUser.getData().getId();
        TagAliasOperatorHelper.getInstance().handleAction(this, aliasBean);
    }

    private void loadShareCate() {

        showProgress("加载分类");
        Map<String, String> params = new HashMap<>();
        params.put("dictType", "MERCHANT_TYPE");
        VolleyUtil.getInstance().doPost(APIAddress.SHARE_CATE, params, new TypeToken<ShareCate>() {
        }.getType(), "");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(ShareCate shareCate) {
        dismissProgress();
        if (shareCate.getCode().equals("0")) {
            for (ShareCate.Dict data : shareCate.getData()) {
                Cache.shareCache.put(data.getId(), new ArrayList<Shares.ShareItem>());
            }

            Cache.shareCate = shareCate;
            checkMenu(0);
        } else {
            commonDialog = new CommonDialog(this);
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
    public void onResult(AMapLocation amapLocation) {
        dismissProgress();
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            //解析定位结果
            SPUtil.insertString(this, SPUtil.KEY_LATITUDE, amapLocation.getLatitude() + "");
            SPUtil.insertString(this, SPUtil.KEY_LONGITUDE, amapLocation.getLongitude() + "");
            SPUtil.insertString(this, SPUtil.KEY_CITY, amapLocation.getCity());
            onLocationSuccess();
        } else {
            if (SPUtil.getString(this, SPUtil.KEY_LATITUDE).equals("") ||
                    SPUtil.getString(this, SPUtil.KEY_LONGITUDE).equals("") ||
                    SPUtil.getString(this, SPUtil.KEY_CITY).equals("")) {
                startActivityForResult(new Intent(this, CitySelectActivity.class), REQUEST_CODE_LOCATION);
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(MerchantVo merchant){
        dismissProgress();

        if(merchant.getCode().equals("0")){
            Intent intent=new Intent(this, ShareActivity.class);
            intent.putExtra("merchant",merchant.getData());

            startActivity(intent);
        }else{
            showDialog("商户信息加载失败，"+merchant.getMsg()+",请稍后再试");
        }
    }
    private void onLocationSuccess() {
        tvCity.setText(SPUtil.getString(this, SPUtil.KEY_CITY));
        //初始化分类
        if (Cache.shareCate == null) {
            loadShareCate();
        } else {
            checkMenu(0);
        }
    }



    private int currentMenu = -1;
    private Fragment[] fragments;

    public void checkMenu(int i) {
        if (i == currentMenu)
            return;
//        layoutMenuMain.setBackgroundColor(ContextCompat.getColor(this, i == 0 ? R.color.button_gray : R.color.button_white));
        ivMenuMain.setImageResource(i == 0 ? R.drawable.menu_main_checked : R.drawable.menu_main_normal);
        tvMenuMain.setTextColor(ContextCompat.getColor(this, i == 0 ? R.color.main_yellow : R.color.color_border));
        ivMenuMerchant.setImageResource(i == 1 ? R.drawable.menu_merchant_checked : R.drawable.menu_merchant_checked);
        tvMenuMerchant.setTextColor(ContextCompat.getColor(this, i == 1 ? R.color.main_yellow : R.color.color_border));
        ivMenuAccount.setImageResource(i == 2 ? R.drawable.menu_account_checked : R.drawable.menu_account_normal);
        tvMenuAccount.setTextColor(ContextCompat.getColor(this, i == 2 ? R.color.main_yellow : R.color.color_border));
//        layoutMenuShare.setBackgroundColor(ContextCompat.getColor(this, i == 1 ? R.color.button_gray : R.color.button_white));
//        layoutMenuMerchant.setBackgroundColor(ContextCompat.getColor(this, i == 1 ? R.color.button_gray : R.color.button_white));
//        layoutMenuAccount.setBackgroundColor(ContextCompat.getColor(this, i == 2 ? R.color.button_gray : R.color.button_white));
//        layoutMenuMine.setBackgroundColor(ContextCompat.getColor(this, i == 3 ? R.color.button_gray : R.color.button_white));
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        if (fragments[i] == null) {
            switch (i) {
                case 0:
                    fragments[i] = new MainFragment();
                    break;
                case 1:
                    if (Cache.currenUser.getData().getRoles().equals(RegisterActivity.ROLE_USER)) {
//                        fragments[i] = new ShareEntraFragment();
                    } else {
                        fragments[i] = new MerchantFragment();
                    }

                    break;
                case 2:
                    fragments[i] = new AccountFragment();
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
        if(requestCode==REQUEST_CODE_MINE&&resultCode==501){
            finish();
        }
        if (requestCode == REQUEST_CODE_LOCATION && resultCode == 200) {
            onLocationSuccess();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(4);
        finish();
    }

    @OnClick({R.id.btn_mine, R.id.layout_menu_main, R.id.layout_menu_account, R.id.layout_menu_share, R.id.layout_menu_merchant, R.id.btn_weishare, R.id.btn_localshare})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_mine:
                startActivityForResult(new Intent(this,MineActivity.class),REQUEST_CODE_MINE);
                break;
            case R.id.layout_menu_main:
                checkMenu(0);
                break;
            case R.id.layout_menu_account:
                checkMenu(2);
                break;
            case R.id.layout_menu_share:
                toggleShareLayout();
                break;
            case R.id.layout_menu_merchant:
                checkMenu(1);
                break;
            case R.id.btn_weishare:
                showMerchantIdDialog();
                break;
            case R.id.btn_localshare:
                startActivity(new Intent(this,ShareActivity.class));
                break;
        }
        if(view.getId()!=R.id.layout_menu_share){
            hideShareLayout();
        }
    }

    private void showMerchantIdDialog(){
        inputDialog=new CommonInputDialog(this);
        inputDialog.setDescribe("请输入商户号:").setHint("商户号").setOKButton(View.VISIBLE, null, new CommonInputDialog.OnOkClickListener() {
            @Override
            public void onClick(String companyid) {
                if(TextUtils.isEmpty(companyid)){
                    Toast.makeText(MainActivity.this,"商户号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

//                        if(companyid.length()<6){
//                            Toast.makeText(getContext(),"商户号应为不小于6位数字",Toast.LENGTH_SHORT).show();
//                            return;
//                        }
                inputDialog.dismiss();
                showProgress("正在加载商户信息");
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("token", Cache.currenUser.getMsg());
                params.put("merchantId",companyid);
                VolleyUtil.getInstance().doPost(APIAddress.GET_MERCHANT,params,new TypeToken<MerchantVo>(){}.getType(),"getMerchant");
            }
        }).show();
    }
    public void toggleShareLayout(){
        boolean toShow=layoutShare.getVisibility()!=View.VISIBLE;
        ivMenuShare.setImageResource(toShow?R.drawable.menu_share_checked:R.drawable.menu_share_normal);
        tvMenuShare.setTextColor(ContextCompat.getColor(this, toShow ? R.color.main_yellow : R.color.color_border));
        layoutShare.setVisibility(toShow?View.VISIBLE:View.GONE);
    }
    public void hideShareLayout(){
        if(layoutShare.getVisibility()==View.VISIBLE){
            toggleShareLayout();
        }
    }
}
