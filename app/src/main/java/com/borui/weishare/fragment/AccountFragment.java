package com.borui.weishare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borui.weishare.PickCashActivity;
import com.borui.weishare.R;
import com.borui.weishare.alipay.AliUtil;
import com.borui.weishare.alipay.AuthResult;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.UserVo;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by borui on 2017/6/29.
 */
public class AccountFragment extends BaseFragment {


    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_curr_account)
    TextView tvCurrAccount;
    @BindView(R.id.tv_year_account)
    TextView tvYearAccount;
    @BindView(R.id.btn_cash)
    Button btnCash;
    @BindView(R.id.tv_operate_weixin)
    TextView tvOperateWeixin;
    @BindView(R.id.layout_bind_weixin)
    LinearLayout layoutBindWeixin;
    @BindView(R.id.tv_operate_alipay)
    TextView tvOperateAlipay;
    @BindView(R.id.layout_bind_alipay)
    LinearLayout layoutBindAlipay;
    View rootView;
    boolean alipayAuthed;
    boolean weixinAuthed;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_account, null);

            ButterKnife.bind(this, rootView);

            initView();
        } else {
            ((ViewGroup) rootView.getRootView()).removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        tvCurrAccount.setText(Cache.getInstance().getCurrenUser().getData().getBalance()+"");
        alipayAuthed=isAuthed("1");
        weixinAuthed=isAuthed("2");
        tvOperateAlipay.setText(alipayAuthed?"解除绑定":"立即绑定");
        tvOperateWeixin.setText(weixinAuthed?"解除绑定":"立即绑定");
    }

    private boolean isAuthed(String type){
        if(Cache.getInstance().getCurrenUser().getData().getAuths()==null||Cache.getInstance().getCurrenUser().getData().getAuths().size()==0)
            return false;

        for (UserVo.UserBean.AuthsBean auth:Cache.getInstance().getCurrenUser().getData().getAuths()) {
            if(auth.getAuthenticationType().equals(type)){
                return true;
            }
        }
        return false;
    }

    @OnClick({R.id.tv_details, R.id.btn_cash, R.id.layout_bind_weixin, R.id.layout_bind_alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_details:
                break;
            case R.id.btn_cash:
                if(alipayAuthed||weixinAuthed){
                    Intent intent=new Intent(getContext(), PickCashActivity.class);
                    intent.putExtra("alipayAuthed",alipayAuthed);
                    intent.putExtra("weixinAuthed",weixinAuthed);
                    startActivity(intent);
                }else{
                    showDialog("您还未绑定微信/支付宝账户，请先绑定！");
                }

                break;
            case R.id.layout_bind_weixin:
                Toast.makeText(getContext(),"正在努力开发，敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_bind_alipay:
                AliUtil.authV2(getActivity());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(AuthResult authResult) {
        String resultStatus = authResult.getResultStatus();

        // 判断resultStatus 为“9000”且result_code
        // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
        if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
            // 获取alipay_open_id，调支付时作为参数extern_token 的value
            // 传入，则支付账户为该授权账户
//            Toast.makeText(PickCashActivity.this,
//                    "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                    .show();
            authAlipay(authResult.getAuthCode());

        } else {
            // 其他状态值则为授权失败
            Toast.makeText(getActivity(),
                    "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

        }
    }
    private void authAlipay(String auth_token){
        HashMap<String,String> params=new HashMap<>();
        params.put("token",Cache.getInstance().getCurrenUser().getMsg());
        params.put("userId",Cache.getInstance().getCurrenUser().getData().getId()+"");
        params.put("auth_token",auth_token);
        params.put("auth_type","1");
        VolleyUtil.getInstance().doPost(APIAddress.ALIPAY_WEIXIN_AUTH,params,new TypeToken<BaseVo>(){}.getType(),"authAlipay");
        showProgress("正在绑定支付宝账户");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
        dismissProgress();
        if(baseVo.getTag().equals("authAlipay")){
            if(baseVo.getCode().equals("0")){
                Toast.makeText(getContext(),"绑定成功",Toast.LENGTH_SHORT).show();
                UserVo.UserBean.AuthsBean authsBean=new UserVo.UserBean.AuthsBean();
                authsBean.setAuthenticationType("1");
                if(Cache.getInstance().getCurrenUser().getData().getAuths()==null){
                    Cache.getInstance().getCurrenUser().getData().setAuths(new ArrayList<UserVo.UserBean.AuthsBean>());
                }
                Cache.getInstance().getCurrenUser().getData().getAuths().add(authsBean);
                initView();
            }else{
                Toast.makeText(getContext(),"绑定失败，"+baseVo.getMsg(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
