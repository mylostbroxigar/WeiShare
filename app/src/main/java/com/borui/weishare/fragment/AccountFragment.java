package com.borui.weishare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borui.weishare.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by borui on 2017/6/29.
 */

public class AccountFragment extends Fragment {

    @BindView(R.id.tv_curr_account)
    TextView tvCurrAccount;
    @BindView(R.id.tv_year_account)
    TextView tvYearAccount;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_cash)
    TextView tvCash;
    @BindView(R.id.tv_card_type)
    TextView tvCardType;
    @BindView(R.id.tv_set_paypass)
    TextView tvSetPaypass;
    @BindView(R.id.layout_bind)
    LinearLayout layoutBind;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_details, R.id.tv_cash, R.id.layout_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_details:
                break;
            case R.id.tv_cash:
                break;
            case R.id.layout_bind:
                break;
        }
    }
}
