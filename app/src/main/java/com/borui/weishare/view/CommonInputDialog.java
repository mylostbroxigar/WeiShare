package com.borui.weishare.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.borui.weishare.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by borui on 2017/9/4.
 */

public class CommonInputDialog extends Dialog {


    @BindView(R.id.common_tv_title)
    TextView commonTvTitle;
    @BindView(R.id.common_et_input)
    EditText commonEtInput;
    @BindView(R.id.common_btn_cancle)
    TextView commonBtnCancle;
    @BindView(R.id.view_devider)
    View viewDevider;
    @BindView(R.id.common_btn_ok)
    TextView commonBtnOk;


    public interface OnOkClickListener {
        public void onClick(String input);
    }

    public CommonInputDialog(Context context) {
        super(context, R.style.dialog_style);
        setContentView(R.layout.common_edit_dialog);
        ButterKnife.bind(this);
        setCancelable(false);
        setOKButton(View.VISIBLE, null, new OnOkClickListener() {
            @Override
            public void onClick(String input) {
                dismiss();
            }
        });
        setCancleButton(View.VISIBLE, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("===============", "onClick: cancle");
                dismiss();
            }
        });
    }

    public CommonInputDialog setCancleAble(boolean cancleAble) {
        this.setCancelable(cancleAble);
        return this;
    }

    public CommonInputDialog setTitle(String des) {
        commonTvTitle.setText(des);
        return this;
    }

    public CommonInputDialog setHint(String hint) {
        commonEtInput.setHint(hint);
        return this;
    }
    public CommonInputDialog setInputType(int inputType) {
        commonEtInput.setInputType(inputType);
        return this;
    }
    public CommonInputDialog setOKButton(int visable, String text, final OnOkClickListener okBtnListener) {
        commonBtnOk.setVisibility(visable);
        if (!TextUtils.isEmpty(text))
            commonBtnOk.setText(text);
        if (okBtnListener != null) {
            commonBtnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    okBtnListener.onClick(commonEtInput.getText().toString().trim());
                }
            });
        }

        return this;
    }

    public CommonInputDialog setCancleButton(int visable, String text, View.OnClickListener cancleBtnListener) {
        commonBtnCancle.setVisibility(visable);
        if (!TextUtils.isEmpty(text))
            commonBtnCancle.setText(text);
        if (cancleBtnListener != null)
            commonBtnCancle.setOnClickListener(cancleBtnListener);
        return this;
    }


}
