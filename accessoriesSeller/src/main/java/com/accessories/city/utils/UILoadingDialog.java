package com.accessories.city.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;
import com.accessories.city.R;

/**
 * 显示加载对话框
 * */
public class UILoadingDialog extends Dialog{

    private static UILoadingDialog dialog;

	private UILoadingDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setContentView(R.layout.dialog_loading_common);
		((TextView) findViewById(R.id.progText)).setText("正在为您加载...");

	}

	public static void show(Context context,boolean istouchCancel){
		dialog = new UILoadingDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	public static void dismmis(){
		if (dialog != null){
			dialog.dismiss();
			dialog = null;
		}
	}

}
