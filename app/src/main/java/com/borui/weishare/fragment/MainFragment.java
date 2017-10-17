package com.borui.weishare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.borui.weishare.R;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.vo.ShareCate;
import com.borui.weishare.vo.Shares;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.TabPageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by borui on 2017/6/30.
 */

public class MainFragment extends Fragment {
    String TAG="SharesFragment";
    @BindView(R.id.shares_tab)
    TabPageIndicator sharesTab;
    @BindView(R.id.shares_content)
    ViewPager sharesContent;


    private ContentAdapter contentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shares, null);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
        if(Cache.shareCate==null){
            Map<String,String> params=new HashMap<>();
            params.put("dictType","MERCHANT_TYPE");
            VolleyUtil.getInstance().doPost(APIAddress.SHARE_CATE,params,null,new TypeToken<ShareCate>(){}.getType(),"");
        }
        else
            initView();
        super.onResume();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(ShareCate shareCate){
        if(shareCate.getCode().equals("0")){
            for (ShareCate.Dict data:shareCate.getData()){
                Cache.shareCache.put(data.getId(),new ArrayList<Shares.ShareItem>());
            }

            Cache.shareCate=shareCate;
            initView();
        }
    }

    private void initView(){
        contentAdapter = new ContentAdapter(getChildFragmentManager());// 此处，如果不是继承的FragmentActivity,而是继承的Fragment，则参数应该传入getChildFragmentManager()
        Log.w(TAG, "initView: sharesContent="+sharesContent );
        sharesContent.setAdapter(contentAdapter);
        sharesTab.setViewPager(sharesContent);
        sharesTab.setVisibility(View.VISIBLE);
    }



    public class ContentAdapter extends FragmentStatePagerAdapter {


        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle=new Bundle();
            bundle.putInt("cateCode",Cache.shareCate.getData().get(position).getId());
            Log.e("=======", "getItem: position="+position+"   code=" +Cache.shareCate.getData().get(position).getId());
            return Fragment.instantiate(getActivity(),ShareCateFragment.class.getName(),bundle);
        }

        @Override
        public int getCount() {
            return Cache.shareCate.getData().size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return Cache.shareCate.getData().get(position).getDictName();
        }
    }
}
