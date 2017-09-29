package com.borui.weishare;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borui.weishare.fragment.AccountFragment;
import com.borui.weishare.fragment.MainFragment;
import com.borui.weishare.fragment.MineFragment;
import com.borui.weishare.fragment.ShareFragment;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.DensityUtil;
import com.borui.weishare.util.SPUtil;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.TelAddr;
import com.borui.weishare.vo.UserVo;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener{

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
        DensityUtil.dpi=dm.density;

        layoutMenuMain.setOnClickListener(this);
        layoutMenuShare.setOnClickListener(this);
        layoutMenuAccount.setOnClickListener(this);
        layoutMenuMine.setOnClickListener(this);
        fragments=new Fragment[4];
        checkMenu(0);
        autoLogin();
    }

    private void autoLogin(){
        if(SPUtil.getBoolean(this,SPUtil.KEY_LOGINED)){
            Map<String,String> params=new HashMap<>();
            params.put("username",SPUtil.getString(this,SPUtil.getString(this,SPUtil.KEY_USERNAME)));
            params.put("password",SPUtil.getString(this,SPUtil.getString(this,SPUtil.KEY_PASSWORD)));
            VolleyUtil.getInstance().doPost(APIAddress.LOGIN,params,new TypeToken<UserVo>(){}.getType(),"login");
        }

    }

//    public void getTel(View view) {
//        VolleyUtil.getInstance().doGet("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15007167330", new TypeToken<TelAddr>() {
//        }.getType());
//    }

    @Subscribe
    public void receiveData(Object obj) {
        if (obj instanceof BaseVo) {
            BaseVo basevo = (BaseVo) obj;
        } else if (obj instanceof TelAddr) {
            TelAddr teladdr = (TelAddr) obj;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_menu_main:
                checkMenu(0);
                break;
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

    private int currentMenu=-1;
    private Fragment[] fragments;
    public void checkMenu(int i){
        if(i==currentMenu)
            return;
        layoutMenuMain.setBackgroundColor(ContextCompat.getColor(this, i==0?R.color.button_gray:R.color.button_white));
        layoutMenuShare.setBackgroundColor(ContextCompat.getColor(this,i==1?R.color.button_gray:R.color.button_white));
        layoutMenuAccount.setBackgroundColor(ContextCompat.getColor(this,i==2?R.color.button_gray:R.color.button_white));
        layoutMenuMine.setBackgroundColor(ContextCompat.getColor(this,i==3?R.color.button_gray:R.color.button_white));
        FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
        if(fragments[i]==null){
            switch (i){
                case 0:
                    fragments[i]=new MainFragment();
                    break;
                case 1:
                    fragments[i]=new ShareFragment();
                    break;
                case 2:
                    fragments[i]=new AccountFragment();
                    break;
                case 3:
                    fragments[i]=new MineFragment();
                    break;
            }
            trans.add(R.id.layout_content,fragments[i]);
        }

        hideFragments(trans);
        trans.show(fragments[i]);
        trans.commit();
    }
    private void hideFragments(FragmentTransaction trans){

        for (Fragment fragment:fragments) {
            if(fragment!=null)
                trans.hide(fragment);
        }
    }
}
