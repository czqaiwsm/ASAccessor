package com.accessories.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.accessories.city.R;

import java.util.List;

public class CityAdpter extends BaseAdapter {
    private List<String> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public CityAdpter(Context context, List<String> items) {
        this.mContext = context;
        this.mItemList = items;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mItemList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.choose_city_adapter, null);
            holder.city_name = (TextView)convertView.findViewById(R.id.city_name);
            holder.firstLine = convertView.findViewById(R.id.first_lien);
            holder.halfLine = convertView.findViewById(R.id.half_lien);
            holder.secondLine = convertView.findViewById(R.id.second_lien);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.city_name.setText(mItemList.get(position));
        if(position == 0){
            holder.firstLine.setVisibility(View.VISIBLE);
        }else {
            holder.firstLine.setVisibility(View.GONE);
        }

        if(position == getCount() - 1){
            holder.halfLine.setVisibility(View.GONE);
            holder.secondLine.setVisibility(View.VISIBLE);
        }else {
            holder.halfLine.setVisibility(View.VISIBLE);
            holder.secondLine.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        private TextView city_name;
        private View firstLine;
        private View halfLine;
        private View secondLine;
    }
}
