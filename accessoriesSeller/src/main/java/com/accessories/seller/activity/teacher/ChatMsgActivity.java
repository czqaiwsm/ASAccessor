package com.accessories.seller.activity.teacher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.seller.activity.BaseActivity;
import com.accessories.seller.fragment.teacher.ChatMsgFragment;

public class ChatMsgActivity extends BaseActivity {
		private ChatMsgFragment mFragment;

		@Override
		protected void onCreate(Bundle arg0) {
			super.onCreate(arg0);
			onInitContent();
		}

		private void onInitContent() {
			mFragment = (ChatMsgFragment) Fragment.instantiate(this, ChatMsgFragment.class.getName());
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.add(android.R.id.content, mFragment);
			ft.commit();
		}}