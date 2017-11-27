package com.borui.weishare.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.borui.weishare.R;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.vo.AuditingVo;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhuborui on 2017/11/10.
 */

public class CheckAdapter extends BaseAdapter {
    public interface OnButtonClickListener{
        public void onPassClick(AuditingVo.AuditingBean auditingBean);
        public void onRefuseClick(AuditingVo.AuditingBean auditingBean);
    }
    private Context context;
    private LayoutInflater inflater;
    private List<AuditingVo.AuditingBean> auditingBeanList;

    private OnButtonClickListener onButtonClickListener;
    public CheckAdapter(Context context) {
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    public void changeStatusById(int id,int status){
        for (AuditingVo.AuditingBean auditingBean:auditingBeanList) {
            if(auditingBean.getId()==id){
                auditingBean.setAuditingStatus(status);
                break;
            }
        }
        notifyDataSetChanged();
    }
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.onButtonClickListener=onButtonClickListener;
    }

    public void setAuditingBeanList(List<AuditingVo.AuditingBean> auditingBeanList){
        this.auditingBeanList=auditingBeanList;
        notifyDataSetChanged();

    }
    public void addAuditingBean(AuditingVo.AuditingBean auditingBean){
        this.auditingBeanList.add(0,auditingBean);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return auditingBeanList==null?0:auditingBeanList.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.check_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        final AuditingVo.AuditingBean auditingBean=auditingBeanList.get(i);
        holder.tvOrdernum.setText("单号："+auditingBean.getId());
        holder.tvCustomername.setText("客户："+auditingBean.getUsername());
        holder.tvCommission.setText("佣金："+auditingBean.getCommission()+"元");
        if(TextUtils.isEmpty(auditingBean.getAuditingPicture1())){
            holder.ivScreenshotTimeline.setVisibility(View.GONE);
        }else{
            holder.ivScreenshotTimeline.setVisibility(View.VISIBLE);
            Glide.with(context).load(APIAddress.IMAGEPATH+auditingBean.getAuditingPicture1()).into(holder.ivScreenshotTimeline);
        }

        if(TextUtils.isEmpty(auditingBean.getAuditingPicture2())){
            holder.ivScreenshotElebus.setVisibility(View.GONE);
        }else{
            holder.ivScreenshotElebus.setVisibility(View.VISIBLE);
            Glide.with(context).load(APIAddress.IMAGEPATH+auditingBean.getAuditingPicture2()).into(holder.ivScreenshotElebus);
        }
        if(auditingBean.getAuditingStatus()==0){
            holder.btnCheckPass.setVisibility(View.VISIBLE);
            holder.btnCheckRefuse.setVisibility(View.VISIBLE);
            holder.tvCheckStatus.setVisibility(View.GONE);
            holder.btnCheckPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onButtonClickListener!=null)
                        onButtonClickListener.onPassClick(auditingBean);
                }
            });
            holder.btnCheckRefuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onButtonClickListener!=null)
                        onButtonClickListener.onRefuseClick(auditingBean);
                }
            });
        }else if(auditingBean.getAuditingStatus()==1){

            holder.btnCheckPass.setVisibility(View.GONE);
            holder.btnCheckRefuse.setVisibility(View.GONE);
            holder.tvCheckStatus.setVisibility(View.VISIBLE);
            holder.tvCheckStatus.setText("已通过");
        }else if(auditingBean.getAuditingStatus()==2){

            holder.btnCheckPass.setVisibility(View.GONE);
            holder.btnCheckRefuse.setVisibility(View.GONE);
            holder.tvCheckStatus.setVisibility(View.VISIBLE);
            holder.tvCheckStatus.setText("已拒绝");
        }

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
