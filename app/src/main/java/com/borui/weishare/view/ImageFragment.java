package com.borui.weishare.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.borui.weishare.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by borui on 2017/11/16.
 */

public class ImageFragment extends Fragment {

    @BindView(R.id.image_viewpager)
    ViewPager imageViewpager;
    Unbinder unbinder;
    ArrayList<String> imagePaths;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, null);
        unbinder = ButterKnife.bind(this, view);

        imagePaths=getArguments().getStringArrayList("imagePaths");

        imageViewpager.setAdapter(new ImageAdapter(getFragmentManager()));
        Log.e("boruiz", "onCreateView: imagefargment createview" );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    class ImageAdapter extends FragmentStatePagerAdapter{
        public ImageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=new ImageItemFragment();
            Bundle bundle=new Bundle();
            bundle.putString("imagePath",imagePaths.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return imagePaths==null?0:imagePaths.size();
        }
    }
}
