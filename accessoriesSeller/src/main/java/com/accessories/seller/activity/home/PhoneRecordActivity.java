package com.accessories.seller.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.seller.activity.BaseActivity;
import com.accessories.seller.fragment.home.CallPhoneRecordFragment;
import com.accessories.seller.fragment.home.CallPhoneRecordFragment;

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
