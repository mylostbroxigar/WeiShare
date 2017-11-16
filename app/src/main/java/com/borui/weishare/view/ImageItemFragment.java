package com.borui.weishare.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.borui.weishare.R;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.util.ImageUtil;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by borui on 2017/11/16.
 */

public class ImageItemFragment extends Fragment {
    public static Drawable error_drawable;
    @BindView(R.id.iv_image_item)
    ImageView ivImageItem;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_item, null);
        unbinder = ButterKnife.bind(this, view);
        String imagePath=getArguments().getString("imagePath");
        Glide.with(getActivity()).load(APIAddress.IMAGEPATH +imagePath).thumbnail(0.1f).error(error_drawable).into(ivImageItem);
        Log.e("boruiz", "onCreateView: imageitemfargment createview" );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
