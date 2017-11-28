package com.borui.weishare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.ImagePath;
import com.borui.weishare.vo.MerchantVo;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * Created by zhuborui on 2017/11/21.
 */

public class MerchantInfoActivity extends BaseActivity {
    @BindView(R.id.tv_merchantname)
    TextView tvMerchantname;
    @BindView(R.id.tv_merchantnum)
    TextView tvMerchantnum;
    @BindView(R.id.et_commision)
    EditText etCommision;
    @BindView(R.id.iv_main_image)
    ImageView ivMainImage;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    MerchantVo.Merchant merchant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_info);
        ButterKnife.bind(this);

        loadMerchantInfo();
    }

    private void loadMerchantInfo(){
        HashMap<String,String> params=new HashMap<>();
        params.put("token", Cache.getInstance().getCurrenUser().getMsg());
        VolleyUtil.getInstance().doPost(APIAddress.GET_MERCHANT_INFO,params,new TypeToken<MerchantVo>(){}.getType(),"getInfo");
        showProgress("");
    }

    private void updateMerchantInfo(){

        String commissionStr=etCommision.getText().toString().trim();

        if(TextUtils.isEmpty(commissionStr)){
            Toast.makeText(this,"佣金不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        int commission=Integer.parseInt(commissionStr);
        if(commission<=0){
            Toast.makeText(this,"佣金至少为1元",Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String,String> params=new HashMap<>();
        params.put("token", Cache.getInstance().getCurrenUser().getMsg());
        params.put("id", merchant.getId()+"");
        params.put("commission",commissionStr);
        if(!TextUtils.isEmpty(imagePath)){
            List<ImagePath> images=new ArrayList<>();
            ImagePath ip=new ImagePath(imagePath,"pages");
            images.add(ip);
            VolleyUtil.getInstance().doPost(APIAddress.UPDATE_MERCHANT_INFO,params,images,new TypeToken<BaseVo>(){}.getType(),"updateInfo");
        }else{
            VolleyUtil.getInstance().doPost(APIAddress.UPDATE_MERCHANT_INFO,params,new TypeToken<BaseVo>(){}.getType(),"updateInfo");
        }
        showProgress("正在修改");
    }

    @OnClick({R.id.iv_main_image, R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main_image:
                selectImage();
                break;
            case R.id.btn_update:
                updateMerchantInfo();
                break;
        }
    }
    private String imagePath;
    private void selectImage() {

        RxGalleryFinal.with(this)
                .image()
                .multiple()
                .maxSize(1)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        imagePath = imageMultipleResultEvent.getResult().get(0).getOriginalPath();
                        Glide.with(MerchantInfoActivity.this).load(new File(imagePath)).centerCrop().override(ivMainImage.getWidth(), ivMainImage.getHeight()).into(ivMainImage);
                    }

                }).openGallery();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
        dismissProgress();
        if( baseVo instanceof MerchantVo){
            onResult((MerchantVo)baseVo);
        }else{
            if(baseVo.getCode().equals("0")){
                showDialog("修改成功");
            }else{
                showDialog("修改失败，"+baseVo.getMsg());
            }
        }
    }

    public void onResult(MerchantVo merchantVo) {
        if(merchantVo.getCode().equals("0")&&merchantVo.getData()!=null){
            merchant=merchantVo.getData();
            tvMerchantname.setText(merchant.getMerchantName());
            tvMerchantnum.setText(merchant.getId()+"");
            etCommision.setText(merchant.getCommission()+"");

            if(!TextUtils.isEmpty(merchant.getPageURL()))
                Glide.with(this).load(APIAddress.IMAGEPATH+merchant.getPageURL()).into(ivMainImage);
        }else{
            commonDialog=new CommonDialog(this);
            commonDialog.setContent("获取商户信息失败，是否重试？").setCancleButton(getResources().getString(R.string.cancle), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialog.dismiss();
                    finish();
                }
            }).setOKButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonDialog.dismiss();
                }
            }).show();
        }

    }
}
