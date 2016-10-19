package com.accessories.city.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import com.accessories.city.fragment.GuideFragment;
import com.accessories.city.utils.AppManager;

public class GuideActivity extends BaseActivity {

	private GuideFragment m_obj_Fragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initContent();
	}

	private void initContent() {
		if (m_obj_Fragment == null) {
			m_obj_Fragment = (GuideFragment) Fragment.instantiate(this,
					GuideFragment.class.getName());
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.add(android.R.id.content, m_obj_Fragment);
			ft.commit();
		}
	}

	private long firstTime = 0;
	@Override
	public void onBackPressed() {
		long secondTime = System.currentTimeMillis();
		if (secondTime - firstTime > 2000) {// 如果两次按键时间间隔大于2秒，则不退出
			Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
			firstTime = secondTime;// 更新firstTime
		} else {// 否则退出程序
			AppManager.getAppManager().finishActivity(this);
			System.gc();
			System.exit(0);
		}
		super.onBackPressed();
	}

	/*
    @Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {// 如果两次按键时间间隔大于2秒，则不退出
				Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
				firstTime = secondTime;// 更新firstTime
				return true;
			} else {
				AppManager.getAppManager().finishActivity(this);
				System.gc();
				System.exit(0);// 否则退出程序
			}
		}
		return super.onKeyUp(keyCode, event);
	}*/

}
