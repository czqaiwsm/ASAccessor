package com.accessories.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.MsgDetail;
import com.accessories.city.utils.ImageLoaderUtil;
import com.accessories.city.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MsgAdpter extends BaseAdapter {
    private List<MsgDetail> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public MsgAdpter(Context context, List<MsgDetail> items) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.msg_adapter, null);
            holder.headPhoto = (RoundImageView)convertView.findViewById(R.id.head_photo);
            holder.name = (TextView)convertView.findViewById(R.id.Name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.msgContent = (TextView)convertView.findViewById(R.id.msg_content);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        MsgDetail message = mItemList.get(position);
        if(message != null){
            ImageLoader.getInstance().displayImage(message.getHeadImg(),holder.headPhoto,ImageLoaderUtil.mHallIconLoaderOptions);
            holder.name.setText(message.getStudentName());
            holder.time.setText(message.getCreateTime());
//            holder.name.setText(message.get());
            holder.msgContent.setText(message.getContent());
        }
        return convertView;
    }

    class ViewHolder {
        private RoundImageView headPhoto;
        private TextView name;
        private TextView time;
        private TextView msgContent;
    }
}
