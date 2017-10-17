package com.borui.weishare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.borui.weishare.LoginActivity;
import com.borui.weishare.net.Cache;
import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.view.CommonProgressDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by borui on 2017/9/29.
 */

public class BaseFragment extends Fragment {
    public CommonProgressDialog commonProgress;
    public CommonDialog commonDialog;

    public static final int REQUEST_LOGIN=0x101;

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

    public void showProgress(String msg){
        dismissProgress();
        commonProgress=new CommonProgressDialog(getContext());
        commonProgress.setMessage(msg);
        commonProgress.show();
    }

    public void dismissProgress(){
        if(commonProgress!=null&&commonProgress.isShowing()){
            commonProgress.dismiss();
        }
    }

    public void showDialog(String msg){
        dismissDialog();
        commonDialog=new CommonDialog(getContext());
        commonDialog.removeCancleButton().setContent(msg).show();
    }

    public void dismissDialog(){
        if(commonDialog!=null&&commonDialog.isShowing()){
            commonDialog.dismiss();
        }
    }

    public boolean checkLogin(){
        if(Cache.currenUser==null){
            startActivityForResult(new Intent(getContext(), LoginActivity.class),REQUEST_LOGIN);
            return false;
        }
        return true;
    }
}
