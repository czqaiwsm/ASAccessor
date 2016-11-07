package com.accessories.city.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.accessories.city.R;

import java.util.List;
import java.util.Map;

public class CourseAdapter extends BaseAdapter {
    private List<Map<String,String>> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();
    }

    public CourseAdapter(Context context, List<Map<String,String>> items) {
        this.mContext = context;
        this.mItemList = items;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.course_adapter,null);
            holder.courseImg = (ImageView) convertView.findViewById(R.id.courseImg);
            holder.courseName = (TextView) convertView.findViewById(R.id.couseName);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        // int width = LayoutParams.MATCH_PARENT;
        // int height = width;
        // LayoutParams params = new LayoutParams(width, height);
        // holder.mIcon.setLayoutParams(params);
//        ImageLoader.getInstance().displayImage(mItemList.get(position).getImage(), holder.mIcon, ImageLoaderUtil.mHallIconLoaderOptions);
        Map<String,String> map = mItemList.get(position);
        if(map != null){
            if(!TextUtils.isEmpty(map.get("cuorseName"))){
                holder.courseName.setText(map.get("cuorseName"));
            }

            String strImg = map.get("cuorseImg");
            if(!TextUtils.isEmpty(strImg)){
                 holder.courseImg.setBackground(mContext.getResources().getDrawable(Integer.valueOf(strImg))); Integer.valueOf(strImg);
            }
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView courseImg;
        private TextView courseName;
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
