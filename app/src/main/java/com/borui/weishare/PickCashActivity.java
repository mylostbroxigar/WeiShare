package com.borui.weishare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.borui.weishare.net.Cache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by borui on 2017/11/14.
 */

public class PickCashActivity extends Activity {
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.et_pick_num)
    TextView etPickNum;
    @BindView(R.id.btn_pay)
    Button btnPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickcash);
        ButterKnife.bind(this);

//        tvBalance.setText(Cache.currenUser.getData().getBalance()+"元");
        tvBalance.setText("5元");
    }

    @OnClick(R.id.btn_pay)
    public void onViewClicked() {

    }
}
