package com.borui.weishare;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borui.weishare.net.Cache;
import com.borui.weishare.vo.BaseVo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by borui on 2017/11/14.
 */

public class PickCashActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_pick_num)
    EditText etPickNum;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.cb_pick_weixin)
    CheckBox cbPickWeixin;
    @BindView(R.id.layout_weixin)
    LinearLayout layoutWeixin;
    @BindView(R.id.cb_pick_alipay)
    CheckBox cbPickAlipay;
    @BindView(R.id.layout_alipay)
    LinearLayout layoutAlipay;
    @BindView(R.id.btn_pick)
    Button btnPick;
    boolean alipayAuthed;
    boolean weixinAuthed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickcash);
        ButterKnife.bind(this);
        alipayAuthed=getIntent().getBooleanExtra("alipayAuthed",false);
        weixinAuthed=getIntent().getBooleanExtra("weixinAuthed",false);
        initView();

    }
    private void initView(){
        if(alipayAuthed&&weixinAuthed){
            cbPickAlipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        cbPickWeixin.setChecked(false);
                    }
                }
            });
            cbPickWeixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        cbPickAlipay.setChecked(false);
                    }
                }
            });
            cbPickWeixin.setChecked(true);
        }
        if(alipayAuthed&&!weixinAuthed){
            layoutWeixin.setVisibility(View.GONE);
            cbPickAlipay.setChecked(true);
        }
        if(!alipayAuthed&&weixinAuthed){
            layoutAlipay.setVisibility(View.GONE);
            cbPickWeixin.setChecked(true);
        }
        tvBalance.setText("账户余额："+Cache.getInstance().getCurrenUser().getData().getBalance()+"元");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
        dismissProgress();
//        if (baseVo.getTag().equals("auth")) {
//            if (baseVo.getCode().equals("0")) {
//                commonDialog=new CommonDialog(this);
//                commonDialog.setContent("提交成功").removeCancleButton().setOKButton("确定", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        commonDialog.dismiss();
//                        setResult(200);
//                        finish();
//                    }
//                });
//
//            } else {
//                showDialog("提交失败：" + baseVo.getMsg());
//            }
//
//
//        }


    }

    @OnClick({R.id.iv_back, R.id.btn_pick})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_pick:
                String numStr=etPickNum.getText().toString().trim();
                if(TextUtils.isEmpty(numStr)){
                    showDialog("请输入提现金额");
                    return;
                }
                double num=Double.parseDouble(numStr);
                if(num>Cache.getInstance().getCurrenUser().getData().getBalance()){
                    showDialog("余额不足");
                    return;
                }
                if(!cbPickWeixin.isChecked()&&!cbPickAlipay.isChecked()){
                    showDialog("请选择提现方式");
                    return;
                }
                break;
        }
    }
}
