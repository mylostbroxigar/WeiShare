package com.borui.weishare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.DensityUtil;
import com.borui.weishare.util.ImageUtil;
import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.Company;
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
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * Created by borui on 2017/7/12.
 */

public class SendScreenShotActivity extends BaseActivity {
    MerchantVo merchantVo;
    @BindView(R.id.iv_screenshot_timeline)
    ImageView ivScreenshotTimeline;
    @BindView(R.id.layout_screenshot_timeline)
    LinearLayout layoutScreenshotTimeline;
    @BindView(R.id.iv_screenshot_elebus)
    ImageView ivScreenshotElebus;
    @BindView(R.id.layout_screenshot_elebus)
    LinearLayout layoutScreenshotElebus;
    @BindView(R.id.btn_share_submit)
    Button btnShareSubmit;

    MediaBean timeline_shot;
    MediaBean elebus_shot;
    @BindView(R.id.iv_del_timeline)
    ImageView ivDelTimeline;
    @BindView(R.id.iv_del_elebus)
    ImageView ivDelElebus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        ButterKnife.bind(this);
        merchantVo = (MerchantVo) getIntent().getSerializableExtra("merchant");

        int imageWidth = DensityUtil.screenWidth / 2 - 40;
        int imageHeight = imageWidth * DensityUtil.screenHeight / DensityUtil.screenWidth;

        ViewGroup.LayoutParams iv1Params = ivScreenshotTimeline.getLayoutParams();
        iv1Params.height = imageHeight;
        iv1Params.width = imageWidth;
        ivScreenshotTimeline.setLayoutParams(iv1Params);


        ViewGroup.LayoutParams iv2Params = ivScreenshotElebus.getLayoutParams();
        iv2Params.height = imageHeight;
        iv2Params.width = imageWidth;
        ivScreenshotElebus.setLayoutParams(iv1Params);

        setTimelineLayout();
        setELebusLayout();
        layoutScreenshotElebus.setVisibility(merchantVo.getData().getMerchantType().equals("3")? View.VISIBLE : View.GONE);
    }


    @OnClick({R.id.iv_screenshot_timeline, R.id.iv_screenshot_elebus, R.id.btn_share_submit,R.id.iv_del_timeline, R.id.iv_del_elebus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_screenshot_timeline:
                if (timeline_shot == null) {
                    RxGalleryFinal.with(this)
                            .image()
                            .multiple()
                            .maxSize(1)
                            .imageLoader(ImageLoaderType.GLIDE)
                            .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                                @Override
                                protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                                    timeline_shot = imageMultipleResultEvent.getResult().get(0);
                                   setTimelineLayout();
                                }

                            }).openGallery();
                }
                break;
            case R.id.iv_screenshot_elebus:
                if (elebus_shot == null) {
                    RxGalleryFinal.with(this)
                            .image()
                            .multiple()
                            .maxSize(1)
                            .imageLoader(ImageLoaderType.GLIDE)
                            .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                                @Override
                                protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                                    elebus_shot = imageMultipleResultEvent.getResult().get(0);
                                    setELebusLayout();
                                }

                            }).openGallery();
                }
                break;
            case R.id.iv_del_timeline:
                timeline_shot=null;
                setTimelineLayout();
                break;
            case R.id.iv_del_elebus:
                elebus_shot=null;
                setELebusLayout();
                break;
            case R.id.btn_share_submit:
                HashMap<String,String> params=new HashMap<>();
                params.put("token", Cache.currenUser.getMsg());
//                params.put("merchantId",company.get);
                params.put("userId",Cache.currenUser.getData().getId()+"");
                List<String> images=new ArrayList<>();
                images.add(timeline_shot.getOriginalPath());
                if(merchantVo.getData().getMerchantType().equals("3"))
                    images.add(elebus_shot.getOriginalPath());
                VolleyUtil.getInstance().doPost(APIAddress.SEND_SCREENSHOT,params,images,new TypeToken<BaseVo>(){}.getType(),"sendScreenshot");
                showProgress("正在提交");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
        dismissProgress();
        if (baseVo.getTag().equals("sendScreenshot")) {
            if (baseVo.getCode().equals("0")) {
                commonDialog=new CommonDialog(this);
                commonDialog.setContent("提交成功").removeCancleButton().setOKButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commonDialog.dismiss();
                        setResult(200);
                        finish();
                    }
                });

            } else {
                showDialog("提交失败：" + baseVo.getMsg());
            }


        }


    }
    private void setELebusLayout(){
        if(elebus_shot==null){

            Glide.with(this).load(R.drawable.icon_add).fitCenter().override(50,50).into(ivScreenshotElebus);
            ivDelElebus.setVisibility(View.GONE);
        }else{
            Glide.with(SendScreenShotActivity.this).load(elebus_shot.getOriginalPath()).centerCrop().into(ivScreenshotElebus);
            ivDelElebus.setVisibility(View.VISIBLE);
        }
    }

    private void setTimelineLayout(){
        if(timeline_shot==null){
            Glide.with(this).load(R.drawable.icon_add).fitCenter().override(50,50).into(ivScreenshotTimeline);
            ivDelTimeline.setVisibility(View.GONE);
        }else{

            Glide.with(SendScreenShotActivity.this).load(timeline_shot.getOriginalPath()).centerCrop().into(ivScreenshotTimeline);
            ivDelTimeline.setVisibility(View.VISIBLE);
        }
    }
}
