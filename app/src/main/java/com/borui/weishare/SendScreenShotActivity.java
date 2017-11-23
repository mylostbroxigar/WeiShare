package com.borui.weishare;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.DensityUtil;
import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.ImagePath;
import com.borui.weishare.vo.MerchantVo;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    MerchantVo.Merchant merchant;
    MediaBean timeline_shot;
    MediaBean elebus_shot;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_commission)
    TextView tvCommission;
    @BindView(R.id.iv_timeline)
    ImageView ivTimeline;
    @BindView(R.id.iv_elebus)
    ImageView ivElebus;
    @BindView(R.id.btn_share_submit)
    Button btnShareSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        ButterKnife.bind(this);
        merchant = getIntent().getParcelableExtra("merchant");
        tvCommission.setText("佣金："+merchant.getCommission()+"元");
//        int imageWidth = DensityUtil.screenWidth / 2 - 40;
//        int imageHeight = imageWidth * DensityUtil.screenHeight / DensityUtil.screenWidth;
//
//        ViewGroup.LayoutParams iv1Params = ivTimeline.getLayoutParams();
//        iv1Params.height = imageHeight;
//        iv1Params.width = imageWidth;
//        ivTimeline.setLayoutParams(iv1Params);
//
//
//        ViewGroup.LayoutParams iv2Params = ivElebus.getLayoutParams();
//        iv2Params.height = imageHeight;
//        iv2Params.width = imageWidth;
//        ivElebus.setLayoutParams(iv1Params);
//        setTimelineLayout();
//        setELebusLayout();
        ivElebus.setVisibility(merchant.getMerchantType().equals(RegisterActivity.ROLE_COMPANY_ONLINE) ? View.VISIBLE : View.GONE);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
        dismissProgress();
        if (baseVo.getTag().equals("sendScreenshot")) {
            if (baseVo.getCode().equals("0")) {
                commonDialog = new CommonDialog(this);
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

    private void setELebusLayout() {
        if (elebus_shot != null) {
            Glide.with(SendScreenShotActivity.this).load(elebus_shot.getOriginalPath()).centerCrop().into(ivElebus);
        }
    }

    private void setTimelineLayout() {
        if (timeline_shot != null) {
            Glide.with(SendScreenShotActivity.this).load(timeline_shot.getOriginalPath()).centerCrop().into(ivTimeline);
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_timeline, R.id.iv_elebus, R.id.btn_share_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_timeline:
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
                break;
            case R.id.iv_elebus:
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
                break;
            case R.id.btn_share_submit:
                HashMap<String, String> params = new HashMap<>();
                params.put("token", Cache.currenUser.getMsg());
                params.put("merchantId", merchant.getId() + "");
                params.put("userId", Cache.currenUser.getData().getId() + "");
                List<ImagePath> images = new ArrayList<>();
                images.add(new ImagePath(timeline_shot.getOriginalPath(), "auditingfile1"));
                if (merchant.getMerchantType().equals(RegisterActivity.ROLE_COMPANY_ONLINE))
                    images.add(new ImagePath(elebus_shot.getOriginalPath(), "auditingfile2"));
                VolleyUtil.getInstance().doPost(APIAddress.SEND_SCREENSHOT, params, images, new TypeToken<BaseVo>() {
                }.getType(), "sendScreenshot");
                showProgress("正在提交");
                break;
        }
    }
}
