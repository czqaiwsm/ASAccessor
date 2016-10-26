package com.accessories.city.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public  class MainAdapter extends BaseAdapter {
    private List mItemList;
    private SoftReference<Context> mContext;

    @Override
    public int getCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public MainAdapter(SoftReference<Context> context) {
        this.mContext = context;
    }




    @Override
    public Object getItem(int position) {

        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    public void addList(List list){
        if(mItemList == null) mItemList = new ArrayList();

        if(list != null){
            this.mItemList.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void restList(List list){
        if(mItemList == null) mItemList = new ArrayList();
        mItemList.clear();
        mItemList.addAll(list);

    }

    public List getList(){
        return mItemList;
    }

}
