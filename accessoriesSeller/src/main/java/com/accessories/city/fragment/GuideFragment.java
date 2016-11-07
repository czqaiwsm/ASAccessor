package com.accessories.city.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.accessories.city.R;
import com.accessories.city.activity.login.LoginActivity;
import com.accessories.city.adapter.GuideViewPagerAdapter;


/**
 * 引导界面Fragment
 * */
public class GuideFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.guide, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		m_obj_Layoutview = view;
		m_obj_viewPager = (ViewPager) view.findViewById(R.id.id_guide_viewpager);
		initView();
		m_obj_viewPager.setAdapter(m_obj_adapter);

		((GuideViewPagerAdapter)m_obj_adapter).setAutoPlay(m_obj_viewPager,false);
		m_obj_viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				int pos = position % ids.length;
				((GuideViewPagerAdapter) m_obj_adapter).moveCursorTo(pos);// 点的移动
				if (pos == ids.length - 1) {
					((GuideViewPagerAdapter)m_obj_adapter).setAutoPlay(m_obj_viewPager,false);

					handler.sendEmptyMessageDelayed(TO_THE_END, 1000);// 延时3s调到主界面

				} else {
					handler.sendEmptyMessage(LEAVE_FROM_END);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	/************************* private methods and param **************************/
	private ViewPager m_obj_viewPager = null;
	private PagerAdapter m_obj_adapter = null;
	private View m_obj_Layoutview = null;
	private int ids[] = { R.drawable.guide_01, R.drawable.guide_02};
	private int TO_THE_END = 0;// 到达最后一张图片
	private int LEAVE_FROM_END = 1;// 离开最后一张图片

	private void initView() {
		m_obj_adapter = new GuideViewPagerAdapter(ids, m_obj_Layoutview,
				getActivity());
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == TO_THE_END) {
				if(!GuideFragment.this.isDetached() && isAdded()){
//						startActivity(new Intent(getActivity(), MainActivity.class));
						startActivity(new Intent(getActivity(), LoginActivity.class));
					getActivity().finish();
				}
			} else if (msg.what == LEAVE_FROM_END) {
				handler.removeMessages(TO_THE_END);
			}
		}
	};


}
