package com.borui.weishare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.borui.weishare.R;
import com.borui.weishare.ShareActivity;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.view.CommonProgressDialog;
import com.borui.weishare.vo.Company;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by borui on 2017/6/29.
 */

public class ShareFragment extends Fragment {

    @BindView(R.id.et_company_id)
    EditText etCompanyId;
    @BindView(R.id.btn_search_company)
    Button btnSearchCompany;
    CommonProgressDialog progress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_search_company)
    public void onViewClicked() {
        String companyid=etCompanyId.getText().toString();
        if(TextUtils.isEmpty(companyid)){
            Toast.makeText(getContext(),"商户号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if(companyid.length()<6){
            Toast.makeText(getContext(),"商户号应为不小于6位数字",Toast.LENGTH_SHORT).show();
            return;
        }
        progress=new CommonProgressDialog(getContext());
        progress.show();
        VolleyUtil.getInstance().doGetFromAssets(getContext(), APIAddress.GETCOMPANY,new TypeToken<Company>(){}.getType(),"company");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(Object obj){
        if(progress!=null&&progress.isShowing())
            progress.dismiss();

        if(obj instanceof Company){
            Company company=(Company)obj;
            Intent intent=new Intent(getContext(), ShareActivity.class);
            intent.putExtra("company",company);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
