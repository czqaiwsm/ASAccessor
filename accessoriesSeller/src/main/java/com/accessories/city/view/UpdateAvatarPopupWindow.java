package com.accessories.city.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.accessories.city.R;

/**
 * ����ͷ��
 * @author ccs7727@163.com
 * @time 2015��9��30������3:30:26
 * 
 */
public class UpdateAvatarPopupWindow extends PopupWindow {

	private TextView btn_camera, btn_photo, btn_cancel;
	private View mPopView;

	public UpdateAvatarPopupWindow(Activity context, View view, OnClickListener itemsOnClick) {
		super(view);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPopView = inflater.inflate(R.layout.alert_dialog2, null);
		btn_camera = (TextView) mPopView.findViewById(R.id.btn_camera);
		btn_photo = (TextView) mPopView.findViewById(R.id.btn_photo);
		btn_cancel = (TextView) mPopView.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dismiss();
			}
		});
		btn_photo.setOnClickListener(itemsOnClick);
		btn_camera.setOnClickListener(itemsOnClick);
		this.setContentView(mPopView);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.AnimBottom);
		ColorDrawable dw = new ColorDrawable(0x66000000);
		this.setBackgroundDrawable(dw);
		mPopView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				int height = mPopView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}

}
