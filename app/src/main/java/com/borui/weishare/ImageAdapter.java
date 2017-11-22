package com.borui.weishare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.borui.weishare.util.ImageUtil;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * Created by zhuborui on 2017/7/5.
 */

public class ImageAdapter extends BaseAdapter {
    Context context;
    List<String> urls;
    int imageSize;
    public ImageAdapter(Context context,int imageSize) {
        this.context = context;
        urls = new ArrayList<>();
        this.imageSize=imageSize;
    }


    public List<String> getUrls(){
        return urls;
    }
    public void addUrls(List<String> urls) {
        this.urls.addAll(urls);
        if (this.urls.size() > 8) {
            this.urls = this.urls.subList(0, 8);
        }
        notifyDataSetChanged();
    }

    public void clearUrls(){
        this.urls.clear();
        this.urls=new ArrayList<>();
        notifyDataSetChanged();
    }

    public boolean isAddButton(int i){
        return i==urls.size();
    }


    @Override
    public int getCount() {
        int count=urls.size()<8?urls.size()+1:urls.size();
        return count;
    }
    public int getImageCount() {
        return urls.size();
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
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.image_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        if(i<urls.size()){
            File file=new File(urls.get(i));
            Glide.with(context).load(file).centerCrop().override(imageSize,imageSize).into(holder.iv);
            holder.ivDel.setVisibility(View.VISIBLE);
            holder.ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageUtil.deleteImage(urls.remove(i));
                    notifyDataSetChanged();
                }
            });
        }else if(i==urls.size()){
            Glide.with(context).load(R.drawable.btn_image_add).centerCrop().override(imageSize,imageSize).into(holder.iv);
            holder.ivDel.setVisibility(View.GONE);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.iv_del)
        ImageView ivDel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
