package com.borui.weishare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.borui.weishare.PayActivity;
import com.borui.weishare.R;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.vo.AuditingVo;
import com.borui.weishare.vo.BaseVo;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by borui on 2017/11/10.
 */

public class MerchantFragment extends BaseFragment {
    View rootView;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;
    @BindView(R.id.lv_check)
    ListView lvCheck;
    Unbinder unbinder;
    CheckAdapter adapter;
    @BindView(R.id.rl_check)
    SwipeRefreshLayout rlCheck;
    @BindView(R.id.tv_commission)
    TextView tvCommission;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_merchant, null);
            unbinder = ButterKnife.bind(this, rootView);
            rlCheck.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadAuditing(false);
                }
            });
            adapter = new CheckAdapter(getContext());
            adapter.setOnButtonClickListener(new CheckAdapter.OnButtonClickListener() {
                @Override
                public void onPassClick(AuditingVo.AuditingBean auditingBean) {
                    auditing(auditingBean,1);
                }

                @Override
                public void onRefuseClick(AuditingVo.AuditingBean auditingBean) {
                    auditing(auditingBean,2);
                }
            });
            lvCheck.setAdapter(adapter);
        } else {
            ((ViewGroup) rootView.getRootView()).removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvCommission.setText(Cache.currenUser.getData().getBalance()+"");
        loadAuditing(true);
    }

    private void loadAuditing(boolean showProgress) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", Cache.currenUser.getMsg());
        params.put("auditingStatus", "0");
        VolleyUtil.getInstance().doPost(APIAddress.GET_MERCHANT_AUDITING, params, new TypeToken<AuditingVo>() {
        }.getType(), "loadAuditing");
        if (showProgress)
            showProgress("");
    }

    private void auditing(AuditingVo.AuditingBean auditingBean,int status){
        HashMap<String, String> params = new HashMap<>();
        params.put("token", Cache.currenUser.getMsg());
        params.put("id", auditingBean.getId()+"");
        params.put("auditingStatus", status+"0");
        params.put("comment","");
        VolleyUtil.getInstance().doPost(APIAddress.MERCHANT_AUDITING, params, new TypeToken<BaseVo>() {
        }.getType(), "auditing#"+auditingBean.getId()+"#"+status);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
        if(baseVo instanceof AuditingVo){
            onResult((AuditingVo)baseVo);
        }else{
            if(baseVo.getTag().startsWith("auditing")){
                if(baseVo.getCode().equals("0")){

                    String[] strs=baseVo.getTag().split("#");
                    int id=Integer.parseInt(strs[1]);
                    int status=Integer.parseInt(strs[2]);
                    adapter.changeStatusById(id,status);
                }
            }
        }
    }
    public void onResult(AuditingVo auditingVo) {
        dismissProgress();
        rlCheck.setRefreshing(false);
        if (auditingVo.getTag().equals("loadAuditing")&&auditingVo.getData()!=null) {
            adapter.setAuditingBeanList(auditingVo.getData());
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btn_recharge)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), PayActivity.class));
    }
}
