package com.borui.weishare;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.DensityUtil;
import com.borui.weishare.util.ImageUtil;
import com.borui.weishare.util.SPUtil;
import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.view.DictSelectDialog;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.ImagePath;
import com.borui.weishare.vo.MerchantVo;
import com.borui.weishare.vo.ShareCate;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * Created by zhuborui on 2017/7/4.
 */

public class ShareActivity extends BaseActivity {


    MerchantVo.Merchant merchant;
    ImageAdapter imageAdapter;
    DictSelectDialog dictDialog;

    private static final int REQUEST_SEND_TIMELINE = 0x201;
    private static final int REQUEST_SEND_SCREENSHOT = 0x202;

    private static final int HANDLER_SHOW_GRID = 0x101;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.grid_select_image)
    GridView gridSelectImage;
    @BindView(R.id.cb_addto_share)
    CheckBox cbAddtoShare;
    @BindView(R.id.layout_addto_share)
    RelativeLayout layoutAddtoShare;
    @BindView(R.id.tv_dict)
    TextView tvDict;
    @BindView(R.id.layout_dict)
    LinearLayout layoutDict;
    @BindView(R.id.btn_share)
    Button btnShare;
    int selectDict=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        merchant = getIntent().getParcelableExtra("merchant");
        if (merchant == null) {
            tvTitle.setText("本地分享");
            layoutAddtoShare.setVisibility(View.GONE);
            layoutDict.setVisibility(View.VISIBLE);
            tvDict.setText(Cache.getInstance().getShareCate().getData().get(selectDict).getDictName());
        } else {
            tvTitle.setText("微分享");
            layoutAddtoShare.setVisibility(View.VISIBLE);
            layoutDict.setVisibility(View.GONE);
        }

        int imageSize = (DensityUtil.screenWidth - DensityUtil.dip2px(this,20+4*16))/4 ;
        imageAdapter = new ImageAdapter(ShareActivity.this, imageSize);
//        ViewGroup.LayoutParams layoutParams = gridSelectImage.getLayoutParams();
//        layoutParams.height = imageSize * 3 + 24;
//        gridSelectImage.setLayoutParams(layoutParams);
        gridSelectImage.setAdapter(imageAdapter);
        gridSelectImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (imageAdapter.isAddButton(i)) {
                    selectImage();
                }
            }
        });

//        ArrayAdapter<ShareCate.Dict> adapter = new ArrayAdapter<ShareCate.Dict>(this, android.R.layout.simple_spinner_item, Cache.shareCate.getData());
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDict.setAdapter(adapter);
    }

    private void selectImage() {

        RxGalleryFinal.with(this)
                .image()
                .multiple()
                .maxSize(8-imageAdapter.getImageCount())
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        ArrayList<String> imageUris = new ArrayList<String>();
                        for (MediaBean mediaBean : imageMultipleResultEvent.getResult()) {
                            String imgPath = ImageUtil.compressImage(mediaBean.getOriginalPath(), 720, 960);
                            if (!TextUtils.isEmpty(imgPath))
                                imageUris.add(imgPath);
                        }
                        imageAdapter.addUrls(imageUris);
                    }

                }).openGallery();
    }


    //    private String getRealFilePath( final Uri uri ) {
//        if ( null == uri ) return null;
//        final String scheme = uri.getScheme();
//        String data = null;
//        if ( scheme == null )
//            data = uri.getPath();
//        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
//            data = uri.getPath();
//        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
//            Cursor cursor = getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
//            if ( null != cursor ) {
//                if ( cursor.moveToFirst() ) {
//                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
//                    if ( index > -1 ) {
//                        data = cursor.getString( index );
//                    }
//                }
//                cursor.close();
//            }
//        }
//        return data;
//    }


    private void shareToTimeline(String comment) {
        Intent intent = new Intent();
        //分享精确到微信的页面，朋友圈页面，或者选择好友分享页面
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        intent.putExtra("Kdescription", comment);
        //添加Uri图片地址
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        for (String imgPath : imageAdapter.getUrls()
                ) {
            imageUris.add(Uri.fromFile(new File(imgPath)));
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        startActivityForResult(intent, REQUEST_SEND_TIMELINE);
    }

    private void share(String comment,String merchanType) {
        Map<String, String> params = new HashMap<>();
        params.put("token", Cache.getInstance().getCurrenUser().getMsg());
        params.put("userId", Cache.getInstance().getCurrenUser().getData().getId() + "");
        params.put("longitude", SPUtil.getString(this, SPUtil.KEY_LONGITUDE));
        params.put("latitude", SPUtil.getString(this, SPUtil.KEY_LATITUDE));
        params.put("title", comment);
        params.put("merchantType", merchanType);
        params.put("remark", "");

        List<ImagePath> imagePaths = new ArrayList<>();
        for (String str : imageAdapter.getUrls()) {
            imagePaths.add(new ImagePath(str, "file"));
        }
        VolleyUtil.getInstance().doPost(APIAddress.LOCAL_SHARE, params, imagePaths, new TypeToken<BaseVo>() {
        }.getType(), "localshare");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
        dismissProgress();
        if (baseVo.getTag().equals("localshare") && merchant == null) {
            if (baseVo.getCode().equals("0")) {
                showDialog("上传成功");

                for (String string : imageAdapter.getUrls()) {
                    ImageUtil.deleteImage(string);
                }
                imageAdapter.clearUrls();
                etComment.setText("");
                commonDialog=new CommonDialog(this);
                commonDialog.removeCancleButton().setOKButton("上传成功", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commonDialog.dismiss();
                        finish();
                    }
                }).show();
            } else {
                showDialog("上传失败：" + baseVo.getMsg());
            }


        }


    }

    private void doShare() {
        String comment = etComment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            Toast.makeText(this, "评语不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageAdapter.getUrls().size() == 0) {
            Toast.makeText(this, "必须添加图片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (merchant != null) {
            shareToTimeline(comment);
            if (cbAddtoShare.isChecked()) {
                share(comment,merchant.getMerchantType());
            }
        } else {
            showProgress("正在上传");
            share(comment,Cache.getInstance().getShareCate().getData().get(selectDict).getId()+"");
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("===", "onActivityResult: " + resultCode);
        if (requestCode == REQUEST_SEND_TIMELINE) {
            Intent intent = new Intent(this, SendScreenShotActivity.class);
            intent.putExtra("merchant", merchant);
            startActivityForResult(intent, REQUEST_SEND_SCREENSHOT);
        }
        if (requestCode == REQUEST_SEND_SCREENSHOT && resultCode == 200) {
            finish();
        }
    }


    @OnClick({R.id.iv_back, R.id.layout_dict, R.id.btn_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.layout_dict:
                ArrayList<String> dicts=new ArrayList<>();
                for (ShareCate.Dict dict:Cache.getInstance().getShareCate().getData()
                     ) {
                    dicts.add(dict.getDictName());
                }
                dictDialog=new DictSelectDialog(this, dicts, selectDict, new DictSelectDialog.OnSelectListener() {
                    @Override
                    public void onSelect(int index) {
                        selectDict=index;
                        tvDict.setText(Cache.getInstance().getShareCate().getData().get(selectDict).getDictName());
                    }
                });
                dictDialog.show();
                break;
            case R.id.btn_share:
                doShare();
                break;
        }
    }
}
