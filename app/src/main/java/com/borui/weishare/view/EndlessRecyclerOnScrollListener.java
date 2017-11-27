package com.borui.weishare.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.borui.weishare.net.Cache;
import com.borui.weishare.util.DensityUtil;

/**
 * Created by borui on 2017/10/18.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private int maxHeight;
    public EndlessRecyclerOnScrollListener(int maxheight){
        this.maxHeight=maxheight;
    }
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        int lastPosition=-1;
        StaggeredGridLayoutManager layoutManager=(StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        layoutManager.invalidateSpanAssignments();
        if(newState==RecyclerView.SCROLL_STATE_IDLE){
            int[] lastPositions = new int[layoutManager.getSpanCount()];
            layoutManager.findLastVisibleItemPositions(lastPositions);
            lastPosition = findMax(lastPositions);
        }
        if(lastPosition == layoutManager.getItemCount()-1){
            if(layoutManager.getItemCount()>=10)
                onLoadMore();
        }
    }
    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    public abstract void onLoadMore();
}
