package com.accessories.city.view.tab;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class TabAdapter {
    List<String> tabsList = new ArrayList<String>();
    List<Integer> imgsList = new ArrayList<Integer>();

    public abstract View getView(int position);

    public int getCount() {
        return tabsList.size();
    }

    public void add(String name) {
        tabsList.add(name);
    }

    public void add(int id) {
        imgsList.add(id);
    }

}
