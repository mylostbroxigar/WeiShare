package com.borui.weishare.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.borui.weishare.R;
import com.borui.weishare.vo.Shares;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by borui on 2017/6/30.
 */

public class ShareAdapter extends BaseAdapter {


    private Context context;
    private Shares shares;
    private RequestOptions requestOptions;

    public ShareAdapter(Context context) {
        this.context = context;
        requestOptions=new RequestOptions().centerCrop();
    }

    public void setShares(Shares shares) {
        this.shares = shares;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (shares == null || shares.getData() == null)
            return 0;
        else
            return shares.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_shares_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Shares.DataBean shareData=shares.getData().get(position);
        Glide.with(context).load(shareData.getCover()).apply(requestOptions).into(holder.ivShareThumb);
        Glide.with(context).load(shareData.getHead()).into(holder.ivHead);
        holder.tvShareComment.setText(shareData.getComment());
        holder.tvLike.setText(""+shareData.getLikenum());
        holder.tvCollect.setText(""+shareData.getCollectnum());
        holder.tvName.setText(shareData.getNickname());
        holder.tvSign.setText(shareData.getSign());

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_share_thumb)
        ImageView ivShareThumb;
        @BindView(R.id.tv_share_comment)
        TextView tvShareComment;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_like)
        TextView tvLike;
        @BindView(R.id.tv_collect)
        TextView tvCollect;
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_sign)
        TextView tvSign;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
