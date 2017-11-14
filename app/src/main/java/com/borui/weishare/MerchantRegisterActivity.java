package com.borui.weishare;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.ImagePath;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * Created by borui on 2017/11/13.
 */

public class MerchantRegisterActivity extends BaseActivity {
    @BindView(R.id.et_merchantname)
    EditText etMerchantname;
    @BindView(R.id.et_merchantaddress)
    EditText etMerchantaddress;
    @BindView(R.id.iv_business_licence)
    ImageView ivBusinessLicence;
    @BindView(R.id.iv_legal_persionalid)
    ImageView ivLegalPersionalid;
    @BindView(R.id.btn_register)
    Button btnRegister;

    String business_licence_image;
    String legal_personalid_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_business_licence, R.id.iv_legal_persionalid, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_business_licence:
                selectImage(ivBusinessLicence,0);
                break;
            case R.id.iv_legal_persionalid:
                selectImage(ivLegalPersionalid,1);
                break;
            case R.id.btn_register:
                doRegister();
                break;
        }
    }

    private void selectImage(final ImageView imageView, final int position) {

        RxGalleryFinal.with(this)
                .image()
                .multiple()
                .maxSize(1)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        String imagePath=imageMultipleResultEvent.getResult().get(0).getOriginalPath();
                        if(position==0){
                            business_licence_image=imagePath;
                        }else{
                            legal_personalid_image=imagePath;
                        }
                        Glide.with(MerchantRegisterActivity.this).load(new File(imagePath)).centerCrop().override(imageView.getWidth(), imageView.getHeight()).into(imageView);
                    }

                }).openGallery();
    }

    private void doRegister(){
        String merchantname=etMerchantname.getText().toString().trim();
        String merchantAddress=etMerchantaddress.getText().toString().trim();

        if (TextUtils.isEmpty(merchantname)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.merchantname)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(merchantname)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.merchantaddress)), Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(business_licence_image)){
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.business_licence)), Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(legal_personalid_image)){
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.legal_personalid)), Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", Cache.currenUser.getMsg());
        params.put("merchantName", merchantname);
        params.put("merchantAddress", merchantAddress);
        params.put("merchantType", Cache.currenUser.getData().getRoles());
        params.put("merchantUsers", Cache.currenUser.getData().getId()+"");
        ArrayList<ImagePath> images=new ArrayList<>();
        images.add(new ImagePath(business_licence_image,"licences"));
        images.add(new ImagePath(legal_personalid_image,"personCard"));
        VolleyUtil.getInstance().doPost(APIAddress.MERCHANT_REGISTER, params, images, new TypeToken<BaseVo>() {
        }.getType(), "merchantregister");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
        dismissProgress();

        if (baseVo.getTag().equals("merchantregister")) {
            if (baseVo.getCode().equals("0")) {
                commonDialog = new CommonDialog(this);
                commonDialog.removeCancleButton().setContent("认证成功").setOKButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commonDialog.dismiss();
                        finish();
                    }
                }).show();
            } else {
                showDialog("认证失败," + baseVo.getMsg());
            }
        }

    }
}
