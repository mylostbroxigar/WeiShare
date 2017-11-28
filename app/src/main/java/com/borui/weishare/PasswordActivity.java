package com.borui.weishare;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.borui.weishare.net.Cache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by borui on 2017/11/10.
 */

public class PasswordActivity extends Activity {
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_pre_password)
    EditText etPrePassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_comfire_password)
    EditText etComfirePassword;
    @BindView(R.id.btn_modify)
    Button btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
        tvUsername.setText(Cache.getInstance().getCurrenUser().getData().getUsername());
    }

    @OnClick(R.id.btn_modify)
    public void onViewClicked() {
        String prepass=etPrePassword.getText().toString().trim();
        String newpass=etNewPassword.getText().toString().trim();
        String comfirmpass=etComfirePassword.getText().toString();

        if(TextUtils.isEmpty(prepass)){
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.pre_password)), Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(newpass)){
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.new_password)), Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(prepass)){
            Toast.makeText(this, getResources().getString(R.string.string_cannot_null, getResources().getString(R.string.pre_password)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (newpass.length() < 6) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_less, getResources().getString(R.string.password), 6), Toast.LENGTH_SHORT).show();
            return;
        }
        if (newpass.length() > 20) {
            Toast.makeText(this, getResources().getString(R.string.string_cannot_more, getResources().getString(R.string.password), 20), Toast.LENGTH_SHORT).show();
            return;
        }
        if(!newpass.equals(comfirmpass)){
            Toast.makeText(this, R.string.password_comfirm_fail, Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO 修改密码
    }
}
