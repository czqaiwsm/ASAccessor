package com.accessories.city.activity;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.accessories.city.R;
import com.accessories.city.activity.teacher.TestThread;
import com.accessories.city.utils.AppManager;
import com.toast.ToasetUtil;

public class TestActivity extends FragmentActivity {

	private EditText phoneNum;
	private Button call;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_test);
		phoneNum = (EditText) findViewById(R.id.phoneNum);
		call = (Button)findViewById(R.id.call);
		final TelephonyManager tm = (TelephonyManager) this.getSystemService(Service.TELEPHONY_SERVICE);
		final Vibrator vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);

		call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new TestThread(vibrator,tm)).start();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum.getText().toString()));
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onDestroy() {

		//exit app
		super.onDestroy();
	}
}
