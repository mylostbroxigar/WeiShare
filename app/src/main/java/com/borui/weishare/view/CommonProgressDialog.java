package com.borui.weishare.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.borui.weishare.R;

public class CommonProgressDialog extends Dialog {

	public CommonProgressDialog(Context context) {
		super(context, R.style.dialog_style);
		setContentView(R.layout.dialog_progress);
		tv_message=(TextView) findViewById(R.id.tv_message);
		setCanceledOnTouchOutside(false);
		setCancelable(false);
	}

	TextView tv_message;
	
	public void setMessage(String message){
		tv_message.setText(message);
	}
	
	
}