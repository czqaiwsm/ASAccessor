package com.accessories.seller.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.accessories.seller.R;

public class WaitLayer {
	private TextView tipTextView;
	private Dialog loadingDialog;
	private Animation hyperspaceJumpAnimation;
	private ImageView spaceshipImage;
	private DialogType dialogType = DialogType.MODALESS;
	private ViewGroup rootView;
	private View  view;

	public DialogType getDialogType(){
		return dialogType;
	}

	public WaitLayer(Context context,DialogType dialogType) {
		this.dialogType = dialogType;
		creatDialog(context);
	}

	private void creatDialog(Context context) {
		if(view == null){
			try {

				view = LayoutInflater.from(context).inflate(R.layout.wait_layer,null);
			}catch (Exception e){
				Log.e("exception",e.getMessage());
			}
		}
		hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context,
				R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage = (ImageView)view.findViewById(R.id.progress1);
		tipTextView = (TextView)view.findViewById(R.id.tipText);
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);

		if(dialogType == DialogType.NOT_NOMAL){//与界面绑定的对话框
			if(context instanceof  Activity){
				rootView = (ViewGroup)((Activity)context).findViewById(android.R.id.content);
			}else {
				Log.e("waitLayer:","params context is not Activity");
			}
		}else {
			if (dialogType == DialogType.MODAL){//模态
				loadingDialog = new Dialog(context, R.style.loading_dialog_dim);// 创建自定义样式dialog
				Window window = loadingDialog.getWindow();
				WindowManager.LayoutParams wl = window.getAttributes();
				wl.dimAmount = 0.0f;
       //		wl.alpha  = 0f;    //这句设置了对话框的透明度
				wl.flags = wl.flags | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
				wl.flags = wl.flags | WindowManager.LayoutParams.FLAG_DIM_BEHIND;
				window.setAttributes(wl);
			}else {//非模态
				loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
				loadingDialog.setCancelable(false);
			}
			loadingDialog.addContentView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
		}
	}

	/**
	 * 显示等待层
	 *
	 */
	public void show() {
		show(null);
	}

	/**
	 * 显示等待层
	 *
	 * @param msg
	 */
	public void show(String msg) {
		if(dialogType == DialogType.NOT_NOMAL){
			if(rootView.getChildAt(rootView.getChildCount() - 1) != view){
				spaceshipImage.startAnimation(hyperspaceJumpAnimation);
				if(view.getParent()==null){
					rootView.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
				}
			}
		}else {
			if (loadingDialog.getWindow() != null
					&& !loadingDialog.getWindow().isActive()) {
				if (msg != null) {
					tipTextView.setText(msg);// 设置加载信息,否则加载默认值
				}else {
					tipTextView.setText("正在加载...");
				}
				if(!loadingDialog.isShowing()){
					spaceshipImage.startAnimation(hyperspaceJumpAnimation);
					loadingDialog.show();
				}
			}
		}
	}

	/**
	 * 关闭等待层
	 */
	public void dismiss() {
		if(dialogType != DialogType.NOT_NOMAL){
			if (loadingDialog != null && loadingDialog.isShowing()) {
				spaceshipImage.clearAnimation();
				loadingDialog.dismiss();
			}
		}else {
			if(rootView != null && view != null){
				spaceshipImage.clearAnimation();
				rootView.removeView(view);
			}
		}
		hyperspaceJumpAnimation.cancel();
	}


	public boolean isShow(){
		if(loadingDialog != null){
			return loadingDialog.isShowing();
		}else {
			int count = rootView.getChildCount();
			if(count==0){
				return false;
			}else {
				return (rootView.getChildAt(count - 1) == view);
			}
		}
	}

	public  enum DialogType{
           MODAL,//模态
		MODALESS,//非模态
		NOT_NOMAL//与界面绑定的对话框
	}

}



