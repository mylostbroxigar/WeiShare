package com.borui.weishare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.view.CommonProgressDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by borui on 2017/6/29.
 */

public class BaseActivity extends FragmentActivity {

    public CommonProgressDialog commonProgress;
    public CommonDialog commonDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public void showProgress(String msg){
        dismissProgress();
        commonProgress=new CommonProgressDialog(this);
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
        commonDialog=new CommonDialog(this);
        commonDialog.removeCancleButton().setContent(msg).show();
    }

    public void dismissDialog(){
        if(commonDialog!=null&&commonDialog.isShowing()){
            commonDialog.dismiss();
        }
    }
}
