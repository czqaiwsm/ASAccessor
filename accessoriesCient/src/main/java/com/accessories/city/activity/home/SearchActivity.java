package com.accessories.city.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.city.activity.BaseActivity;
import com.accessories.city.fragment.home.SearchFragment;

public class SearchActivity extends BaseActivity {
	private SearchFragment mFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		onInitContent();
	}

	private void onInitContent() {
		mFragment = (SearchFragment) Fragment.instantiate(this, SearchFragment.class.getName());
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, mFragment);
		ft.commit();
	}
}
