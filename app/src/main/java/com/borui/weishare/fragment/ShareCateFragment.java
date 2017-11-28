package com.borui.weishare.fragment;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borui.weishare.R;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.DensityUtil;
import com.borui.weishare.util.SPUtil;
import com.borui.weishare.view.EndlessRecyclerOnScrollListener;
import com.borui.weishare.view.pullloadmore.PullLoadMoreRecyclerView;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.Shares;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by borui on 2017/6/29.
 */

public class ShareCateFragment extends BaseFragment {
//    @BindView(R.id.grid_share)
//    PullLoadMoreRecyclerView gridShare;
    @BindView(R.id.grid_share)
    RecyclerView gridShare;
    @BindView(R.id.tv_footer)
    TextView tvFooter;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    ShareCateAdapter adapter;
    StaggeredGridLayoutManager layoutManager;
    int cateCode;
    //    @BindView(R.id.tv_footer)
//    TextView tvFooter;
    int page;
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            initGride();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_cate, null);
        ButterKnife.bind(this, view);
        cateCode = getArguments().getInt("cateCode");
//        isViewCreate=true;
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//               initGride();
//            }
//        },200);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(0, 200);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        handler.sendEmptyMessageDelayed(0, 200);
    }


    private void initGride() {
        if (!isResumed() || !getUserVisibleHint()) {
            return;
        }
        if (adapter != null)
            return;
        setStaggeredGridLayout(2);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCate(true);
            }
        });
        gridShare.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadCate(false);
            }
        });
        int width = gridShare.getWidth() / 2 - DensityUtil.dip2px(getContext(), 10);
        adapter = new ShareCateAdapter(getContext(), cateCode, width);
        gridShare.setAdapter(adapter);


        if (Cache.getInstance().getShareCache(cateCode) == null || Cache.getInstance().getShareCache(cateCode).size()==0) {
            loadCate(true);
        }

    }
    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//        gridShare.setRecycledViewPool();
        gridShare.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = DensityUtil.dip2px(getContext(),5);
                outRect.right = DensityUtil.dip2px(getContext(),5);
                outRect.top = DensityUtil.dip2px(getContext(),10);
                outRect.bottom = DensityUtil.dip2px(getContext(),10);
            }

        });
        gridShare.setLayoutManager(staggeredGridLayoutManager);
    }


    boolean isLoading;
    boolean finish;

    private void loadCate(boolean refresh) {

        if (isLoading)
            return;
        isLoading = true;
        if (refresh) {
            finish = false;
            page = 1;
        }else{
            if(!finish){
                tvFooter.setVisibility(View.VISIBLE);
                tvFooter.setText("正在加载...");
            }else{
                return;
            }
        }
        Map<String, String> params = new HashMap<>();
        params.put("longitude", SPUtil.getString(getActivity(), SPUtil.KEY_LONGITUDE));
        params.put("latitude", SPUtil.getString(getContext(), SPUtil.KEY_LATITUDE));
        params.put("merchantType", cateCode + "");
        params.put("distance", "50");
//        params.put("startTime","");
        params.put("pageSize", "10");
        params.put("page", page + "");
        VolleyUtil.getInstance().doPost(APIAddress.QUERYSHARES, params, new TypeToken<Shares>() {
        }.getType(), "" + cateCode);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo obj) {
        if (obj instanceof Shares) {
            onResult((Shares) obj);
        }
    }


    public void onResult(Shares shares) {

        if (!shares.getTag().equals("" + cateCode))
            return;

        isLoading = false;
        tvFooter.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);

        if (shares.getCode().equals("0")) {

            if (shares.getData() == null || shares.getData().size() == 0) {
                tvFooter.setText("暂无更多数据");
                tvFooter.setVisibility(View.VISIBLE);
                finish = true;
            } else {
                if (page <= 1) {
                    Cache.getInstance().inputShareCache(cateCode,shares.getData(),true);
                    adapter.notifyDataSetChanged();
                } else {

                    Cache.getInstance().inputShareCache(cateCode,shares.getData(),false);
                    adapter.notifyItemRangeChanged(adapter.getItemCount(), shares.getData().size());
                }
                page++;
            }

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
