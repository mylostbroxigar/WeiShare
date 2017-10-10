package com.borui.weishare;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borui.weishare.util.DensityUtil;
import com.borui.weishare.vo.Company;

import java.io.File;
import java.util.ArrayList;

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

public class ShareActivity extends Activity {

    @BindView(R.id.tv_commission)
    TextView tvCommission;
    @BindView(R.id.grid_select_image)
    GridView gridSelectImage;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.btn_share)
    Button btnShare;

    Company company;
    ImageAdapter imageAdapter;

    private static final int REQUEST_SEND_TIMELINE = 0x201;

    private static final int HANDLER_SHOW_GRID = 0x101;
    @BindView(R.id.layout_commission)
    LinearLayout layoutCommission;
    @BindView(R.id.cb_addto_share)
    CheckBox cbAddtoShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        company = (Company) getIntent().getSerializableExtra("company");
        if(company==null){
            layoutCommission.setVisibility(View.GONE);
            cbAddtoShare.setVisibility(View.GONE);
        }else{

            layoutCommission.setVisibility(View.VISIBLE);
            cbAddtoShare.setVisibility(View.VISIBLE);
            tvCommission.setText(company.getCommission() + "元");
        }

        int imageSize = (DensityUtil.screenWidth - DensityUtil.dip2px(20) - 24) / 4;
        imageAdapter = new ImageAdapter(ShareActivity.this, imageSize);
        ViewGroup.LayoutParams layoutParams = gridSelectImage.getLayoutParams();
        layoutParams.height = imageSize * 3 + 24;
        gridSelectImage.setLayoutParams(layoutParams);
        gridSelectImage.setAdapter(imageAdapter);
        gridSelectImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (imageAdapter.isAddButton(i)) {
                    selectImage();
                } else if (imageAdapter.isSubButton(i)) {
                    imageAdapter.setDelMode(true);
                } else {

                }
            }
        });
    }

    private void selectImage() {

        final String TAG = "shareActvity";
        RxGalleryFinal.with(this)
                .image()
                .multiple()
                .maxSize(8)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        imageAdapter.addUrls(imageMultipleResultEvent.getResult());
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
    @Override
    protected void onResume() {
        super.onResume();
    }

    private  void shareToTimeline(String comment){
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
        for (MediaBean mediaBean : imageAdapter.getUrls()
                ) {
            Log.e("========", "onViewClicked: path=" + mediaBean.getOriginalPath() + "   Uri=" + Uri.fromFile(new File(mediaBean.getOriginalPath())));
            imageUris.add(Uri.fromFile(new File(mediaBean.getOriginalPath())));
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        startActivityForResult(intent, REQUEST_SEND_TIMELINE);
    }

    private void share(){

    }
    @OnClick(R.id.btn_share)
    public void onViewClicked() {
        String comment = etComment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            Toast.makeText(this, "评语不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        shareToTimeline(comment);
        share();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("===", "onActivityResult: "+resultCode);
        if (requestCode == REQUEST_SEND_TIMELINE) {
            Intent intent = new Intent(this, SendScreenShotActivity.class);
            intent.putExtra("company", company);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (imageAdapter.isDelMode()) {
            imageAdapter.setDelMode(false);
            return;
        }
        super.onBackPressed();
    }
}
