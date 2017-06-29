package com.borui.weishare.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.borui.weishare.R;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.vo.ShareCate;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by borui on 2017/6/29.
 */

public class MainFragment extends Fragment {

    @BindView(android.R.id.tabs)
    TabWidget tabs;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(R.id.mypage_pager)
    ViewPager mypagePager;
    @BindView(R.id.mypage_tabhost)
    TabHost mypageTabhost;


    private TabsAdapter mTabsAdapter;
    private ArrayList<TextView> tabTvs;
    private ArrayList<View> tabLines;

    private ShareCate shareCate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
        if(shareCate==null)
            VolleyUtil.getInstance().doGetFromAssets(getContext(), APIAddress.SHARE_CATE,new TypeToken<ShareCate>(){}.getType());
        else
            initView();
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(Object obj){
        if(obj instanceof ShareCate){
            shareCate=(ShareCate)obj;
            initView();
        }
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    private void initView()
    {
        mypageTabhost.setup();

        mTabsAdapter=new TabsAdapter(getActivity(),mypageTabhost,mypagePager);
        tabTvs=new ArrayList<>();
        tabLines=new ArrayList<>();
        for (ShareCate.DataBean cateBean:shareCate.getData()) {

            View tabView=LayoutInflater.from(getContext()).inflate(R.layout.tabwidget_layout,null);
            TextView tabTv=(TextView)tabView.findViewById(R.id.tabwidget_tv);
            View tabLine=tabView.findViewById(R.id.tabwidget_line);
            tabTvs.add(tabTv);
            tabLines.add(tabLine);
            tabTv.setText(cateBean.getCatename());
            Bundle bundle=new Bundle();
            bundle.putInt("cateCode",cateBean.getCatecode());
            mTabsAdapter.addTab(mypageTabhost.newTabSpec(cateBean.getCatename()).setIndicator(tabView),
                    ShareCateFragment.class,bundle);
        }
        mypagePager.setCurrentItem(0);
    }


    private class TabInfo
    {
        private String tag;
        private Class<?> clss;
        private Bundle args;

        TabInfo(String _tag,Class<?> _class,Bundle _args)
        {
            tag=_tag;
            clss=_class;
            args=_args;
        }
    }
    private class DummyTabFactory implements TabHost.TabContentFactory
    {
        private Context mContext;

        public DummyTabFactory(Context context)
        {
            mContext=context;
        }

        @Override
        public View createTabContent(String tag)
        {
            View v=new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }
    private class TabsAdapter extends FragmentStatePagerAdapter
            implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener
    {
        private Context mContext;
        private TabHost mypageTabhost;
        private ViewPager mypagePager;
        private ArrayList<TabInfo> mTabs=new ArrayList<TabInfo>();

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager)
        {
            super(activity.getSupportFragmentManager());
            mContext=activity;
            mypageTabhost=tabHost;
            mypagePager=pager;
            mypageTabhost.setOnTabChangedListener(this);
            mypagePager.setAdapter(this);
            mypagePager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec,Class<?> clss,Bundle args)
        {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag=tabSpec.getTag();

            TabInfo info=new TabInfo(tag,clss,args);
            mTabs.add(info);
            mypageTabhost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount()
        {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position)
        {
            TabInfo info=mTabs.get(position);
            Log.e("mainFragment", "getItem: "+position );
            return Fragment.instantiate(mContext,info.clss.getName(),info.args);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void onTabChanged(String tabId)
        {
            int position=mypageTabhost.getCurrentTab();
            mypagePager.setCurrentItem(position);

            for (int i=0;i<tabTvs.size();i++){
                tabTvs.get(i).setTextColor(getResources().getColor(i==position?R.color.text_red:R.color.text_black));
                tabLines.get(i).setBackgroundColor(getResources().getColor(i==position?R.color.text_red:R.color.button_white));
            }
        }

        @Override
        public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            TabWidget widget=mypageTabhost.getTabWidget();
            int oldFocusability=widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mypageTabhost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }
        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    }

}
