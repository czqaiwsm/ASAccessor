package com.accessories.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.TeacherInfo;
import com.accessories.city.view.RoundImageView;

import java.util.List;

public class SearchAdpter extends BaseAdapter {
    private List<TeacherInfo> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public SearchAdpter(Context context, List<TeacherInfo> items) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_adapter, null);
            holder.headPhoto = (RoundImageView)convertView.findViewById(R.id.head_photo);
            holder.name = (TextView)convertView.findViewById(R.id.Name);
            holder.teacheCourse = (TextView) convertView.findViewById(R.id.teache_course);
//            holder.msgContent = (TextView)convertView.findViewById(R.id.msg_content);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        TeacherInfo teacherInfo = mItemList.get(position);
        if(teacherInfo != null){
//            ImageLoader.getInstance().displayImage(teacherInfo.getHeadPhoto(),holder.headPhoto,ImageLoaderUtil.mHallIconLoaderOptions);
//            holder.name.setText(teacherInfo.getName());
//            holder.teacheCourse.setText(teacherInfo.getCourseName());
//            holder.time.setText(TeacherInfo.getTime());
//            holder.msgContent.setText(TeacherInfo.getMsg_content());
        }
        return convertView;
    }

    class ViewHolder {
        private RoundImageView headPhoto;
        private TextView name;
        private TextView teacheCourse;
//        private TextView msgContent;
    }
}
