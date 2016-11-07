package com.accessories.city.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.IdInfo;

import java.util.List;

public class ChooseJoinorAdapter extends BaseAdapter {
    private List<IdInfo> mItemList;
    private Context mContext;

    private String joniorId = "";
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();
    }

    public ChooseJoinorAdapter(Context context, List<IdInfo> items) {
        this.mContext = context;
        this.mItemList = items;
//        joniorId = BaseApplication.getUserInfo().getGrade();

    }
    public ChooseJoinorAdapter(Context context, List<IdInfo> items,String selectId) {
        this.mContext = context;
        this.mItemList = items;
        joniorId = selectId;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.choose_joinor_adapter,null);
            holder.joniorName = (TextView) convertView.findViewById(R.id.jonior);
            holder.joniorSelect = (CheckBox) convertView.findViewById(R.id.select_joinor);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        IdInfo idInfo = mItemList.get(position);
        if(idInfo != null){
           holder.joniorName.setText(idInfo.getName());
            if(idInfo.getIdCode().equals(joniorId)){
                holder.joniorSelect.setChecked(true);
            }else {
                holder.joniorSelect.setChecked(false);
            }
        }
        return convertView;
    }

    class ViewHolder {
        private TextView joniorName;
        private CheckBox joniorSelect;
    }

    @Override
    public Object getItem(int position) {

        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
