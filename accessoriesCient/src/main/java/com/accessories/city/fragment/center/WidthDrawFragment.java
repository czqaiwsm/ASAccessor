package com.accessories.city.fragment.center;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accessories.city.R;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.view.tab.ScrollingTabContainerView;
import com.accessories.city.view.tab.TabsActionBar;
import com.accessories.city.view.tab.TabsAdapter;


public class WidthDrawFragment extends BaseFragment {

    private String key;
    private ViewPager mViewPager;
    private ScrollingTabContainerView mTabContainerView;
    private TabsAdapter mTabsAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // startReqTask(this);
        // mLoadHandler.sendEmptyMessageDelayed(Constant.NET_SUCCESS, 100);// 停止加载框
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_widthdraw, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        setTitleText(R.string.schedule);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void initView(View view) {
        setLeftHeadIcon(0);

        mViewPager = (ViewPager) view.findViewById(R.id.order_viewpager);
        mViewPager.setOffscreenPageLimit(0);
        mTabContainerView = (ScrollingTabContainerView) view.findViewById(R.id.tab_container);
        onInitTabConfig();
    }

    private void onInitTabConfig() {
        TabsActionBar tabsActionBar = new TabsActionBar(getActivity(), mTabContainerView);
        Bundle aliBundle = new Bundle();
        aliBundle.putString("cashType",0+"");// 0支付宝 1微信
        mTabsAdapter = new TabsAdapter(getActivity(), mViewPager, tabsActionBar);
        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.msg, null))
                .setmTabbgDrawableId(R.drawable.login_tab), WidthdrawInfoFragment.class, aliBundle);
        Bundle wxBundle = new Bundle();
        wxBundle.putString("cashType",1+"");// 0支付宝 1微信
        mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.contact, null))
                .setmTabbgDrawableId(R.drawable.login_tab), WidthdrawInfoFragment.class, wxBundle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }


}
