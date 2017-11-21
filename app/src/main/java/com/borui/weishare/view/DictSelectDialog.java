package com.borui.weishare.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.borui.weishare.R;
import com.borui.weishare.net.Cache;
import com.borui.weishare.util.DensityUtil;
import com.borui.weishare.vo.ShareCate;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by zhuborui on 2017/11/21.
 */

public class DictSelectDialog extends Dialog {
    public interface OnSelectListener{
        public void onSelect(int index);
    }
    TextView tvCancle;
    TextView tvOk;
    WheelView wvDict;
    OnSelectListener onSelectListener;
    ArrayList<String> dicts;
    int preIndex;
    public DictSelectDialog(Context context,ArrayList<String> dicts,int preIndex,OnSelectListener onSelectListener) {

        super(context, R.style.dialog_style);
        this.onSelectListener=onSelectListener;
        this.dicts=dicts;
        this.preIndex=preIndex;
        initView();
    }

    private void initView() {

        setContentView(R.layout.dialog_dict_select);
        tvCancle=(TextView) findViewById(R.id.tv_cancle);
        tvOk=(TextView) findViewById(R.id.tv_ok);
        wvDict=(WheelView) findViewById(R.id.wv_dict);

        if(dicts==null)
            return;
        WheelView.LineConfig config = new WheelView.LineConfig();
        config.setColor(getContext().getResources().getColor(R.color.color_border));//设置分割线颜色
        // wheelView_line.setLineConfig(config);
        wvDict.setTextColor(getContext().getResources().getColor(R.color.text_gray),getContext().getResources().getColor(R.color.text_black));//设置选中字体颜色
        wvDict.setTextSize(16);
        wvDict.setItems(dicts);
        wvDict.setSelectedIndex(preIndex);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if(onSelectListener!=null){
                    onSelectListener.onSelect(wvDict.getSelectedIndex());
                }
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = DensityUtil.screenWidth; //设置宽度
        lp.height=DensityUtil.screenHeight;
        this.getWindow().setAttributes(lp);
    }
}
