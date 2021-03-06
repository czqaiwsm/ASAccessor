package com.accessories.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public  class MainAdapter extends BaseAdapter {
    protected List mItemList = new ArrayList();
    protected SoftReference<Context> mContext;

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
        notifyDataSetChanged();
    }

    public List getList(){
        if(mItemList == null) mItemList = new ArrayList();
        return mItemList;
    }

}
