package com.accessories.city.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class CallPhoneRecordAdpter extends BaseAdapter implements View.OnClickListener{
    private List<NewsEntity> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public CallPhoneRecordAdpter(Context context, List<NewsEntity> items) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.phone_recorder_adapter_ui, null);
            holder.headPhoto = (ImageView)convertView.findViewById(R.id.phoneImg);
            holder.phoneNum = (TextView)convertView.findViewById(R.id.phone);
            holder.dateTime = (TextView) convertView.findViewById(R.id.dateTime);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsEntity obj = mItemList.get(position);
        if(obj != null){
//            ImageLoader.getInstance().displayImage(obj.getShopPic(),holder.headPhoto, ImageLoaderUtil.mHallIconLoaderOptions);
            holder.phoneNum.setText(obj.getPhone());
            holder.dateTime.setText(obj.getTime());

            holder.headPhoto.setTag(obj.getPhone());
            holder.phoneNum.setText(obj.getPhone());
            holder.headPhoto.setOnClickListener(this);
            holder.phoneNum.setOnClickListener(this);
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView headPhoto;
        private TextView phoneNum;
        private TextView dateTime;

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + v.getTag().toString()));
        mContext.startActivity(intent);
    }
}
