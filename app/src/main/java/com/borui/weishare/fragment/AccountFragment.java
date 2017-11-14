package com.borui.weishare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borui.weishare.PickCashActivity;
import com.borui.weishare.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by borui on 2017/6/29.
 */
//微信提现
//https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
    //微信授权
// https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317851&token=&lang=zh_CN
//支付宝提现
//https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.JDWvLc&treeId=193&articleId=106236&docType=1
    //支付宝授权
//https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.Pe05YU&treeId=193&articleId=105329&docType=1
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

    private void loadAccount(){

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
                startActivity(new Intent(getContext(), PickCashActivity.class));
                break;
            case R.id.layout_bind:
                break;
        }
    }
}
