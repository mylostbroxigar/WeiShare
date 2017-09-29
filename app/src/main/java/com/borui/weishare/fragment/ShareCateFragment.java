package com.borui.weishare.fragment;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.borui.weishare.R;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.vo.Shares;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by borui on 2017/6/29.
 */

public class ShareCateFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.grid_share)
    RecyclerView gridShare;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading_progress)
    RelativeLayout loadingProgress;

    ShareCateAdapter adapter;
    boolean isVisable=false;
    Shares shares;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_cate, null);
        ButterKnife.bind(this, view);
        gridShare.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter=new ShareCateAdapter(getContext());
        gridShare.setAdapter(adapter);
        gridShare.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 20;
                outRect.right = 20;
                outRect.bottom = 30;
            }

        });
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(getUserVisibleHint())
            loadCate(false);
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void loadCate(boolean fromUser){
        if(isVisable&&getUserVisibleHint()){
            if(!fromUser)
                loadingProgress.setVisibility(View.VISIBLE);
            VolleyUtil.getInstance().doGetFromAssets(getContext(),APIAddress.SHARES,new TypeToken<Shares>(){}.getType());
        }
    }
    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
        isVisable=true;
        loadCate(false);
        super.onResume();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(Object obj){
        refreshLayout.setRefreshing(false);
        loadingProgress.setVisibility(View.GONE);
        if(obj instanceof Shares){
            shares=(Shares)obj;
            adapter.setShares(shares);

        }
    }

    @Override
    public void onRefresh() {
        loadCate(true);
    }
}
