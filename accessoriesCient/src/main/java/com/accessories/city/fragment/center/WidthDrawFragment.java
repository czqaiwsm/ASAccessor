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

    private ViewPager mViewPager;
    private ScrollingTabContainerView mTabContainerView;
    private TabsAdapter mTabsAdapter;

    private int flag = 1;// 1 提现   2发布

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widthdraw, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flag = mActivity.getIntent().getFlags();

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
        switch (flag){
            case 1:
                Bundle aliBundle = new Bundle();
                aliBundle.putString("cashType",0+"");// 0支付宝 1微信
                mTabsAdapter = new TabsAdapter(getActivity(), mViewPager, tabsActionBar);
                mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.msg, null))
                        .setmTabbgDrawableId(R.drawable.login_tab), WidthdrawInfoFragment.class, aliBundle);
                Bundle wxBundle = new Bundle();
                wxBundle.putString("cashType",1+"");// 0支付宝 1微信
                mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.contact, null))
                        .setmTabbgDrawableId(R.drawable.login_tab), WidthdrawInfoFragment.class, wxBundle);
                break;
            case 2:
                Bundle applyBundle = new Bundle();
                applyBundle.putString("cashType",0+"");// 供应
                mTabsAdapter = new TabsAdapter(getActivity(), mViewPager, tabsActionBar);
                mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.apply_publish, null))
                        .setmTabbgDrawableId(R.drawable.login_tab), PublisFragment.class, applyBundle);
                Bundle buyBundle = new Bundle();
                buyBundle.putString("cashType",1+"");// 求购
                mTabsAdapter.addTab(tabsActionBar.newTab().setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.buy_publish, null))
                        .setmTabbgDrawableId(R.drawable.login_tab), PublisFragment.class, buyBundle);
                break;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }


}
