package com.accessories.city.activity.teacher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.city.activity.BaseActivity;
import com.accessories.city.fragment.teacher.EvaluateFragment;

public class EvaluateActivity extends BaseActivity {
	private EvaluateFragment mFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		onInitContent();
	}

	private void onInitContent() {
		mFragment = (EvaluateFragment) Fragment.instantiate(this, EvaluateFragment.class.getName(),getIntent().getBundleExtra("bundle"));
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(android.R.id.content, mFragment);
		ft.commit();
	}
}
