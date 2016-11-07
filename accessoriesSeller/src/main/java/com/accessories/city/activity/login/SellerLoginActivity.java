package com.accessories.city.activity.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.city.activity.BaseActivity;
import com.accessories.city.fragment.login.LoginFramgent;
import com.accessories.city.fragment.login.SellerLoginFramgent;

public class SellerLoginActivity extends BaseActivity {
	private SellerLoginFramgent mFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		onInitContent();
	}

	private void onInitContent() {
		mFragment = (SellerLoginFramgent) Fragment.instantiate(this, SellerLoginFramgent.class.getName());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, mFragment);
		ft.commit();
	}
}
