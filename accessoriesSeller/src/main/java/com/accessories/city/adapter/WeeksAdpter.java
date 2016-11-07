package com.accessories.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.CourseInfo;

import java.util.List;

public class WeeksAdpter extends BaseAdapter {
    private List<CourseInfo> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public WeeksAdpter(Context context, List<CourseInfo> items) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.weeks_adapter, null);
            holder.teacherName = (TextView) convertView.findViewById(R.id.teacher_name);
            holder.courseName = (TextView)convertView.findViewById(R.id.course_name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        CourseInfo message = mItemList.get(position);
        if(message != null){
            holder.teacherName.setText(message.getStudentName());
            holder.courseName.setText(message.getCourseName());
            holder.time.setText(message.getSchooltime());
        }
        return convertView;
    }

    class ViewHolder {
        private TextView teacherName;
        private TextView courseName;
        private TextView time;
    }
}
