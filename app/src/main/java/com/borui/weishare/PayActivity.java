package com.borui.weishare;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by borui on 2017/11/14.
 */

public class PayActivity extends Activity {
    @BindView(R.id.rb_100)
    RadioButton rb100;
    @BindView(R.id.rb_200)
    RadioButton rb200;
    @BindView(R.id.rb_500)
    RadioButton rb500;
    @BindView(R.id.rb_1000)
    RadioButton rb1000;
    @BindView(R.id.rg_money)
    RadioGroup rgMoney;
    @BindView(R.id.btn_pay)
    Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
    }

    private void doPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @OnClick(R.id.btn_pay)
    public void onViewClicked() {
        String orderInfo="";
        doPay(orderInfo);
    }
}
