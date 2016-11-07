package com.accessories.city.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.accessories.city.R;

/**
 * 订单取消弹出框
 */
public class
Bec_nurse_Window extends PopupWindow {

	private View mPopView;
	private String content = "";
	private TextView contentTip ;


	public Bec_nurse_Window(final Activity context, View view, final OnClickListener itemsOnClick) {
		super(view);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPopView = inflater.inflate(R.layout.bec_nurse_pop, null);
//		btn_camera = (TextView) mPopView.findViewById(R.id.btn_camera);
//		btn_photo = (TextView) mPopView.findViewById(R.id.btn_photo);
//		btn_cancel = (TextView) mPopView.findViewById(R.id.btn_cancel);
//		btn_cancel.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				dismiss();
//			}
//		});
//		btn_photo.setOnClickListener(itemsOnClick);
//		btn_camera.setOnClickListener(itemsOnClick);
		contentTip = (TextView) mPopView.findViewById(R.id.contentTip);
		this.setContentView(mPopView);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.FILL_PARENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.AnimCenter);
		ColorDrawable dw = new ColorDrawable(0x66000000);
		this.setBackgroundDrawable(dw);
		setOutsideTouchable(false);
//		mPopView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dismiss();
//			}
//		});
//		mPopView.findViewById(R.id.pop_layout).setOnClickListener(null);
		mPopView.findViewById(R.id.commit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				itemsOnClick.onClick(v);
			}
		});

	}

	public String getContent(){

		return content;
	}

	public void setContent(String content){
		contentTip.setText(content);
		this.content = content;
	}

}
