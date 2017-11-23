package com.borui.weishare.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.borui.weishare.R;
import com.borui.weishare.ShareDetailActivity;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.util.ImageUtil;
import com.borui.weishare.view.CornerImageView;
import com.borui.weishare.vo.Shares;
import com.bumptech.glide.Glide;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by borui on 2017/9/29.
 */

public class ShareCateAdapter extends RecyclerView.Adapter<ShareCateAdapter.ViewHolder> {

    private Context context;
    private int cateCode;
    private int frameWidth;
//
//    public interface OnOperateClickListener{
//        public void onLikeClick(int position);
//        public void onCollectClick(int position);
//    }
//    private OnOperateClickListener onOperateClickListener;
//
//    public void setOnOperateClickListener(OnOperateClickListener onOperateClickListener){
//        this.onOperateClickListener=onOperateClickListener;
//    }

    public ShareCateAdapter(Context context,int cateCode,int frameWidth){
        this.context=context;
        this.cateCode=cateCode;
        this.frameWidth=frameWidth;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shares_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Shares.ShareItem shareItem=Cache.shareCache.get(cateCode).get(position);
        ViewGroup.LayoutParams layoutParams=holder.ivShareThumb.getLayoutParams();
        layoutParams.width=frameWidth;
        layoutParams.height=getHeight(shareItem);
        holder.ivShareThumb.setLayoutParams(layoutParams);

        Glide.with(context).load(APIAddress.IMAGEPATH +shareItem.getPics().get(0).getPicPath()).thumbnail(0.1f).placeholder(ImageUtil.getDefultDrawable(context,layoutParams.width,layoutParams.height))
                .error(ImageUtil.getErrorDrawable(context,layoutParams.width,layoutParams.height)).into(holder.ivShareThumb);
        Glide.with(context).load(APIAddress.IMAGEPATH +shareItem.getPersonalPicture()).placeholder(R.drawable.avtar_default).error(R.drawable.avtar_default).into(holder.ivHead);


        holder.tvShareComment.setText(shareItem.getTitle());
        holder.tvLike.setText(""+shareItem.getThumbs());
        holder.tvCollect.setText(""+shareItem.getCollections());
        holder.tvName.setText(shareItem.getRealname());
        holder.tvLocation.setText(getDistance(shareItem.getDistance()));

        holder.ivShareThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShareDetailActivity.class);
                intent.putExtra("shareId",shareItem.getId()+"");
                context.startActivity(intent);
            }
        });
//        if(onOperateClickListener!=null){
//
//            holder.ivCollect.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onOperateClickListener.onCollectClick(position);
//                }
//            });
//            holder.ivLike.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onOperateClickListener.onLikeClick(position);
//                }
//            });
//        }
//        holder.tvSign.setText(shareItem.getSign());
    }


    private String getDistance(double distance){
        if(distance<=1d){
            return "<1KM";
        }else{
            return (int)distance+"KM";
        }
    }
    private int getHeight(Shares.ShareItem shareItem){
        return shareItem.getPics().get(0).getPicHeight()*frameWidth/shareItem.getPics().get(0).getPicWidth();
    }
    @Override
    public int getItemCount() {
        return Cache.shareCache.get(cateCode)==null?0: Cache.shareCache.get(cateCode).size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_share_thumb)
        CornerImageView ivShareThumb;
        @BindView(R.id.tv_share_comment)
        TextView tvShareComment;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_like)
        TextView tvLike;
        @BindView(R.id.tv_collect)
        TextView tvCollect;
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_like)
        ImageView ivLike;
        @BindView(R.id.iv_collect)
        ImageView ivCollect;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
