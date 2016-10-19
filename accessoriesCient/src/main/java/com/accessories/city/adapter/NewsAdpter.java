package com.accessories.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.NewsEntity;
import com.accessories.city.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class NewsAdpter extends BaseAdapter {
    private List<NewsEntity> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public NewsAdpter(Context context, List<NewsEntity> items) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_adapter_ui, null);
            holder.headPhoto = (ImageView)convertView.findViewById(R.id.head_photo);
            holder.name = (TextView)convertView.findViewById(R.id.Name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.msgContent = (TextView)convertView.findViewById(R.id.msg_content);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsEntity obj = mItemList.get(position);
        if(obj != null){
            ImageLoader.getInstance().displayImage(obj.getShopPic(),holder.headPhoto, ImageLoaderUtil.mHallIconLoaderOptions);
//            holder.time.setText(obj.getCreateTime());
            holder.name.setText(obj.getShopName());
            holder.msgContent.setText(obj.getShopDesc());
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView headPhoto;
        private TextView name;
        private TextView time;
        private TextView msgContent;
    }
}
