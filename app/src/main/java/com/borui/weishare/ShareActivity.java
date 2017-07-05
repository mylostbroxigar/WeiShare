package com.borui.weishare;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.borui.weishare.vo.Company;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhuborui on 2017/7/4.
 */

public class ShareActivity extends Activity {

    private static final int REQUEST_CODE_CHOOSE=1;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        company=(Company) getIntent().getSerializableExtra("company");
        tvCommission.setText(company.getCommission()+"元");
        imageAdapter=new ImageAdapter(this,250);
        gridSelectImage.setAdapter(imageAdapter);
        gridSelectImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(imageAdapter.isAddButton(i)){
                    selectImage();
                }else if(imageAdapter.isSubButton(i)){

                }else{

                }
            }
        });
    }

    private void selectImage(){
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))
                .countable(true)
                .maxSelectable(9)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);

            List<String> strUrls=new ArrayList<>();
            for (Uri uri:mSelected ) {
                strUrls.add(getRealFilePath(uri));
            }

            imageAdapter.addUrls(strUrls);
        }

    }
    private String getRealFilePath( final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btn_share)
    public void onViewClicked() {
    }
}
