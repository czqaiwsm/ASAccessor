package com.accessories.city.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.city.activity.BaseActivity;
import com.accessories.city.fragment.home.CallPhoneRecordFragment;
import com.accessories.city.fragment.home.CallPhoneRecordFragment;

public class PhoneRecordActivity extends BaseActivity {
	private CallPhoneRecordFragment mFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		onInitContent();
	}

	private void onInitContent() {
		mFragment = (CallPhoneRecordFragment) Fragment.instantiate(this, CallPhoneRecordFragment.class.getName());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, mFragment);
		ft.commit();
	}
}
