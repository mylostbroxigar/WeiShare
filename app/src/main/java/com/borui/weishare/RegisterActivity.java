package com.borui.weishare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.ImageUtil;
import com.borui.weishare.util.RegexUtil;
import com.borui.weishare.view.CommonDialog;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.ImagePath;
import com.bumptech.glide.Glide;
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
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by borui on 2017/9/25.
 */

public class RegisterActivity extends BaseActivity {

    public static final String ROLE_USER="4";
    public static final String ROLE_COMPANY="3";
    public static final String ROLE_COMPANY_ONLINE="2";

    public static final int RESULT_REQUEST_CODE=0x101;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_realname)
    EditText etRealname;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_personalid)
    EditText etPersonalid;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.rb_role_user)
    RadioButton rbRoleUser;
    @BindView(R.id.rb_role_company)
    RadioButton rbRoleCompany;
    @BindView(R.id.rb_role_company_online)
    RadioButton rbRoleCompanyOnline;
    @BindView(R.id.rg_role)
    RadioGroup rgRole;
    @BindView(R.id.et_telephone)
    EditText etTelephone;
    @BindView(R.id.btn_get_vericode)
    Button btnGetVericode;
    @BindView(R.id.et_vericode)
    EditText etVericode;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;

    String headPath;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        etUsername.setText("boruiz");
        etPassword.setText("123456");
        etRealname.setText("boruiZhu");
        etPersonalid.setText("421023198808133478");
        etEmail.setText("borui_zhu@163.com");
        etTelephone.setText("15007167330");
    }

    int countSec = 60;

    @OnClick({R.id.btn_get_vericode, R.id.btn_register, R.id.iv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_vericode:
                getVericode();
                break;
            case R.id.btn_register:
                doRegister();
                break;
            case R.id.iv_head:
                selectImage();
                break;
        }
    }

    private void selectImage() {

        final String TAG = "shareActvity";
        RxGalleryFinal.with(this)
                .image()
                .multiple()
                .maxSize(1)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
//                        headPath = imageMultipleResultEvent.getResult().get(0).getOriginalPath();
//                        Glide.with(RegisterActivity.this).load(new File(headPath)).centerCrop().override(ivHead.getWidth(), ivHead.getHeight()).into(ivHead);
                            startPhotoZoom(Uri.fromFile(new File(imageMultipleResultEvent.getResult().get(0).getOriginalPath())));
                    }

                }).openGallery();
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 340);
//        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("boruiz", "onActivityResult: ===" );
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_REQUEST_CODE&&resultCode==RESULT_OK){
            if (data.getExtras() != null) {
                Bitmap photo = data.getExtras().getParcelable("data");
                headPath=ImageUtil.saveBitmap(photo,"avtor");
                ivHead.setImageBitmap(photo);
            }
        }
    }
    private void getVericode() {
        String telephone = etTelephone.getText().toString().trim();
        if (TextUtils.isEmpty(telephone)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.telephone)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isCellphone(telephone)) {
            Toast.makeText(this, getResources().getString(R.string.string_not_regex, getResources().getString(R.string.telephone)), Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("phone", telephone);
        VolleyUtil.getInstance().doPost(APIAddress.GETVILIDATECODE, params, new TypeToken<BaseVo>() {
        }.getType(), "getValidateCode");
        btnGetVericode.setEnabled(false);
        handler.sendEmptyMessage(0);
    }

    private void doRegister() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String realname = etRealname.getText().toString().trim();
        String personalid = etPersonalid.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String sex = rgSex.getCheckedRadioButtonId() == R.id.rb_male ? "1" : "0";
        String phone = etTelephone.getText().toString().trim();
        String validataCode = etVericode.getText().toString().trim();
        int roleid = rgRole.getCheckedRadioButtonId();
        String role = "";
        switch (roleid) {
            case R.id.rb_role_user:
                role = "4";
                break;
            case R.id.rb_role_company:
                role = "3";
                break;
            case R.id.rb_role_company_online:
                role = "2";
                break;
            default:
                role = "4";
                break;
        }


        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.username)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() < 6) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_less, getResources().getString(R.string.username), 6), Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() > 20) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_more, getResources().getString(R.string.username), 20), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.password)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_less, getResources().getString(R.string.password), 6), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() > 20) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_more, getResources().getString(R.string.password), 20), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(realname)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.realname)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(personalid)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.personalid)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isPersonalid(personalid)) {
            Toast.makeText(this, getResources().getString(R.string.string_not_regex, getResources().getString(R.string.personalid)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.telephone)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isCellphone(phone)) {
            Toast.makeText(this, getResources().getString(R.string.string_not_regex, getResources().getString(R.string.telephone)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.email)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!RegexUtil.isEmail(email)) {
            Toast.makeText(this, getResources().getString(R.string.string_not_regex, getResources().getString(R.string.email)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(validataCode)) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.vericode)), Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("realname", realname);
        params.put("personalid", personalid);
        params.put("sex", sex);
        params.put("telphone", phone);
        params.put("email", email);
        params.put("roles", role);
        params.put("code", validataCode);
        if (TextUtils.isEmpty(headPath)) {

            VolleyUtil.getInstance().doPost(APIAddress.REGISTER, params,null, new TypeToken<BaseVo>() {
            }.getType(), "register");
        } else {

            List<ImagePath> images = new ArrayList<>();
            images.add(new ImagePath(headPath,"file"));
            VolleyUtil.getInstance().doPost(APIAddress.REGISTER, params, images, new TypeToken<BaseVo>() {
            }.getType(), "register");
        }
        showProgress("正在注册");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
        dismissProgress();

        if (baseVo.getTag().equals("register")) {
            if (baseVo.getCode().equals("0")) {
                commonDialog = new CommonDialog(this);
                commonDialog.removeCancleButton().setContent("注册成功").setOKButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commonDialog.dismiss();
                        finish();
                    }
                }).show();
            } else {
                showDialog("注册失败," + baseVo.getMsg());
            }
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (countSec > 0) {
                        btnGetVericode.setText(countSec + "秒后重试");
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        btnGetVericode.setText("获取验证码");
                        btnGetVericode.setEnabled(true);
                        countSec = 60;
                    }
                    countSec--;
                    break;
            }
        }
    };

}
