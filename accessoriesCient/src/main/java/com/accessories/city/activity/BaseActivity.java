package com.accessories.city.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import com.accessories.city.utils.AppManager;
import com.toast.ToasetUtil;

public class BaseActivity extends FragmentActivity {

	public ToasetUtil toasetUtil = null;
	private EditText editText = null;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		toasetUtil  = new ToasetUtil(this);
		AppManager.getAppManager().addActivity(this);

//		setContentView(R.layout.activity_test);
//		editText = (EditText) findViewById(R.id.phoneNum);
//
//		final TelephonyManager tm = (TelephonyManager) this.getSystemService(Service.TELEPHONY_SERVICE);
//		final Vibrator vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
//
//		findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				new Thread(new TestThread(vibrator,tm)).start();
//				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + editText.getText().toString()));
//				startActivity(intent);
//
//			}
//		});
	}

	@Override
	protected void onDestroy() {

		//exit app
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}
}
