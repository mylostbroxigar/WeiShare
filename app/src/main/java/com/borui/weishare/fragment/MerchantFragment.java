package com.borui.weishare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.borui.weishare.PayActivity;
import com.borui.weishare.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by borui on 2017/11/10.
 */

public class MerchantFragment extends Fragment {
    View rootView;
    @BindView(R.id.iv_recharge)
    ImageView ivRecharge;
    @BindView(R.id.lv_check)
    ListView lvCheck;
    CheckAdapter adapter;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_merchant, null);
        unbinder = ButterKnife.bind(this, rootView);
        adapter = new CheckAdapter(getContext());
        lvCheck.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_recharge)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), PayActivity.class));

    }
}
