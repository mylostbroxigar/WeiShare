package com.borui.weishare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.borui.weishare.fragment.ShareAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhuborui on 2017/7/5.
 */

public class ImageAdapter extends BaseAdapter {
    Context context;
    List<String> urls;

    private RequestOptions requestOptions;
    public ImageAdapter(Context context,int imageSize) {
        this.context = context;
        urls = new ArrayList<>();
        requestOptions=new RequestOptions().centerCrop().override(imageSize,imageSize);
    }

    public void addUrls(List<String> urls) {
        this.urls.addAll(urls);
        if (this.urls.size() > 8) {
            this.urls = this.urls.subList(0, 7);
        }
        notifyDataSetChanged();
    }

    public boolean isAddButton(int i){
        return i==urls.size();
    }

    public boolean isSubButton(int i){
        return i==urls.size()+1;
    }

    @Override
    public int getCount() {

        return urls.size()+2<=8?urls.size()+2:8;
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
            Glide.with(context).load(file).apply(requestOptions).into(holder.iv);
        }else if(i==urls.size()){
            holder.iv.setImageResource(R.drawable.icon_add);
        }else if(i==urls.size()+1){
            holder.iv.setImageResource(R.drawable.icon_sub);
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.check)
        CheckBox check;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
