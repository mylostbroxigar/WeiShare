package com.borui.weishare.fragment;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.borui.weishare.R;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.view.EndlessRecyclerOnScrollListener;
import com.borui.weishare.vo.Shares;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by borui on 2017/6/29.
 */

public class ShareCateFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.grid_share)
    RecyclerView gridShare;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    ShareCateAdapter adapter;
    //    boolean isViewCreate=false;
    StaggeredGridLayoutManager layoutManager;
    int cateCode;
    @BindView(R.id.tv_footer)
    TextView tvFooter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_cate, null);
        ButterKnife.bind(this, view);
        cateCode = getArguments().getInt("cateCode");
        Log.e("======", "onCreateView: cateCode=" + cateCode);
//        isViewCreate=true;
        if (Cache.shareCache.get(cateCode) != null && Cache.shareCache.get(cateCode).size() > 0) {
            setGridShare();
        } else {

            loadCate(true);
        }
        refreshLayout.setOnRefreshListener(this);
        gridShare.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                tvFooter.setText("正在加载更多...");
                loadCate(false);
            }
        });
        return view;
    }

    private void setGridShare() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        gridShare.setLayoutManager(layoutManager);

        gridShare.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();
            }
        });

        adapter = new ShareCateAdapter(getContext(), cateCode);
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
    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        Log.e("======", "setUserVisibleHint: "+isVisibleToUser +"  code="+cateCode);
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser)
//            loadCate(false);
//    }

    private void loadCate(boolean refresh) {

        if (refresh) {

            Cache.shareCache.put(cateCode, new ArrayList<Shares.ShareItem>());
            VolleyUtil.getInstance().doGetFromAssets(getContext(), APIAddress.SHARES, new TypeToken<Shares>() {
            }.getType(), "" + cateCode);
            refreshLayout.setRefreshing(true);
        } else {

            VolleyUtil.getInstance().doGetFromAssets(getContext(), APIAddress.SHARES, new TypeToken<Shares>() {
            }.getType(), "" + cateCode);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(Shares shares) {
        if (!shares.getTag().equals("" + cateCode))
            return;
        refreshLayout.setRefreshing(false);
        tvFooter.setText("");
        if (shares.getCode().equals("0")) {
            if (Cache.shareCache.get(cateCode).size() == 0) {
                Cache.shareCache.get(cateCode).addAll(shares.getData());
                setGridShare();
            } else {
                Cache.shareCache.get(cateCode).addAll(shares.getData());
                adapter.notifyItemRangeChanged(adapter.getItemCount(), shares.getData().size());
            }
        }
    }

    @Override
    public void onRefresh() {
        loadCate(true);
    }

}
