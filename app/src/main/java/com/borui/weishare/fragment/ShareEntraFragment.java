package com.borui.weishare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.borui.weishare.R;
import com.borui.weishare.ShareActivity;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.view.CommonInputDialog;
import com.borui.weishare.view.CommonProgressDialog;
import com.borui.weishare.vo.Company;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by borui on 2017/10/10.
 */

public class ShareEntraFragment extends BaseFragment {
    @BindView(R.id.btn_entra_weshare)
    Button btnEntraWeshare;
    @BindView(R.id.btn_entra_share)
    Button btnEntraShare;
    Unbinder unbinder;
    CommonInputDialog inputDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shrae_entra, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_entra_weshare, R.id.btn_entra_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_entra_weshare:
                inputDialog=new CommonInputDialog(getContext());
                inputDialog.setDescribe("请输入商户号:").setHint("商户号").setOKButton(View.VISIBLE, null, new CommonInputDialog.OnOkClickListener() {
                    @Override
                    public void onClick(String companyid) {
                        if(TextUtils.isEmpty(companyid)){
                            Toast.makeText(getContext(),"商户号不能为空",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(companyid.length()<6){
                            Toast.makeText(getContext(),"商户号应为不小于6位数字",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        inputDialog.dismiss();
                        showProgress("正在加载商户信息");
                        VolleyUtil.getInstance().doGetFromAssets(getContext(), APIAddress.GETCOMPANY,new TypeToken<Company>(){}.getType(),"company");
                    }
                }).show();

                break;
            case R.id.btn_entra_share:
                startActivity(new Intent(getContext(), ShareActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(Company company){
        dismissProgress();

        if(company.getCode().equals("0")){
            Intent intent=new Intent(getContext(), ShareActivity.class);
            intent.putExtra("company",company);
            startActivity(intent);
        }else{
            showDialog("商户信息加载失败，"+company.getMsg()+",请稍后再试");
        }
    }

}
