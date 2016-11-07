package com.accessories.city.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @desc ����һ�仰�������ļ�
 * @creator caozhiqing
 * @data 2016/3/24
 */
public class OrderPageFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments ;

    public OrderPageFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
