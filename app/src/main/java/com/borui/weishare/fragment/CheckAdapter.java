package com.borui.weishare.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.borui.weishare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhuborui on 2017/11/10.
 */

public class CheckAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    public CheckAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.check_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.tvOrdernum.setText("单号："+"000"+i);
        holder.tvCustomername.setText("客户："+"张三");
        holder.tvCommission.setText("佣金："+"3.00元");
        holder.btnCheckPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnCheckPass.setVisibility(View.GONE);
                holder.btnCheckRefuse.setVisibility(View.GONE);
                holder.tvCheckStatus.setText("已通过");
            }
        });
        holder.btnCheckRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnCheckPass.setVisibility(View.GONE);
                holder.btnCheckRefuse.setVisibility(View.GONE);
                holder.tvCheckStatus.setText("已拒绝");
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_ordernum)
        TextView tvOrdernum;
        @BindView(R.id.tv_customername)
        TextView tvCustomername;
        @BindView(R.id.iv_screenshot_elebus)
        ImageView ivScreenshotElebus;
        @BindView(R.id.iv_screenshot_timeline)
        ImageView ivScreenshotTimeline;
        @BindView(R.id.tv_commission)
        TextView tvCommission;
        @BindView(R.id.btn_check_pass)
        Button btnCheckPass;
        @BindView(R.id.btn_check_refuse)
        Button btnCheckRefuse;
        @BindView(R.id.tv_check_status)
        TextView tvCheckStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
