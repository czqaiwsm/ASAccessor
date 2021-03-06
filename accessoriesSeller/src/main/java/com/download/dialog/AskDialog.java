package com.download.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.accessories.seller.R;
import com.download.base.utils.ApkDownloadConfig;

public class AskDialog extends AlertDialog implements OnClickListener {
	public interface OnAskDialogClickListener {
		void onAskDialogConfirm();

		void onAskDialogCancel();
	}

	private OnAskDialogClickListener mListener;
	TextView mTitle;
	TextView mContent;
	Button mConfirm;
	Button mCancel;
	private String mTitleStr;
	private String mMsgStr;
	private Context mContext;

	public void setListener(OnAskDialogClickListener clickListener) {
		mListener = clickListener;
	}

	public AskDialog(Context context, String title, String msg) {
		super(context, R.style.MyDialog);

		mTitleStr = title;
		mMsgStr = msg;

		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_style);

		mTitle = (TextView) findViewById(R.id.dialog_title);
		mContent = (TextView) findViewById(R.id.dialog_content);
		mConfirm = (Button) findViewById(R.id.dialog_confirm);
		mCancel = (Button) findViewById(R.id.dialog_cancel);
		mConfirm.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		mTitle.setText(mTitleStr);
		mContent.setText(mMsgStr);
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER_VERTICAL);

		lp.width = ApkDownloadConfig.SCREEN_WIDTH / 5 * 4;
		dialogWindow.setAttributes(lp);
	}

	public AskDialog(Context context, String title, String msg, String okStr, String cancelStr) {
		super(context, R.style.MyDialog);
		mContext = context;
		mTitleStr = title;
		mMsgStr = msg;
	}

	@Override
	public void cancel() {
		if (mListener != null) {
			mListener.onAskDialogCancel();
		}
		super.cancel();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View view) {

		if (view.getId() == R.id.dialog_confirm) {
			this.dismiss();
			if (mListener != null) {
				mListener.onAskDialogConfirm();
			}
		} else if (view.getId() == R.id.dialog_cancel) {
			if (mListener != null) {
				mListener.onAskDialogCancel();
			}
			this.dismiss();
		}

	}
}
