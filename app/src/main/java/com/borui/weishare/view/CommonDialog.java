package com.borui.weishare.view;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.borui.weishare.R;

/**
 * 通用提示框
 * @author zhuborui
 *
 */
public class CommonDialog extends Dialog {

	public CommonDialog(Context context) {
		super(context, R.style.dialog_style);
		initView();
	}
	private TextView tv_content;
//	private ImageButton ib_cancle;
	private Button btn_ok,btn_cancle;
//	private TextView mtv_content;
	private TextView tv_title;
	private void initView(){
		setContentView(R.layout.common_dialog_layout);
		tv_title=(TextView) findViewById(R.id.common_tv_title);
		tv_content=(TextView) findViewById(R.id.common_tv_content);
		btn_ok=(Button) findViewById(R.id.common_btn_ok);
		btn_cancle=(Button) findViewById(R.id.common_btn_cancle);
		btn_cancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				cancel();
			}
		});
		btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				cancel();
			}
		});
		setCancelable(false);
		setCanceledOnTouchOutside(false);
	}

	public CommonDialog setContentGravity(int gravity){
		tv_content.setGravity(gravity);
		return this;
	}
	public CommonDialog setCancleBtnText(String text){
		btn_cancle.setText(text);
		return this;
	}
	public CommonDialog setTitle(String title){
		tv_title.setText(title);
		return this;
	}
	public CommonDialog setContent(String content){

		if(tv_content!=null)
			tv_content.setText(content);
		return this;
	}
	public CommonDialog setOKButton(String str,View.OnClickListener l){
		if(str!=null&&!str.equals(""))
			btn_ok.setText(str);
		if(l!=null)
			btn_ok.setOnClickListener(l);
		return this;
	}
	public CommonDialog setCancleButton(String str,View.OnClickListener l){
		if(str!=null&&!str.equals(""))
			btn_cancle.setText(str);
		if(l!=null)
			btn_cancle.setOnClickListener(l);
		return this;
	}
	public CommonDialog removeOkButton(){
		btn_ok.setVisibility(View.GONE);
		return this;
	}
	public CommonDialog removeCancleButton(){
		btn_cancle.setVisibility(View.GONE);
		return this;
	}

}
