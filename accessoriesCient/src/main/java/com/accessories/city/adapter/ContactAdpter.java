package com.accessories.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.city.R;
import com.accessories.city.bean.Contactor;
import com.accessories.city.utils.ImageLoaderUtil;
import com.accessories.city.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ContactAdpter extends BaseAdapter {
    private List<Contactor> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public ContactAdpter(Context context, List<Contactor> items) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_adapter, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contactor message = mItemList.get(position);
        if (message != null) {
            ImageLoader.getInstance().displayImage(message.getHeadImg(),holder.headPhoto, ImageLoaderUtil.mHallIconLoaderOptions);
            holder.name.setText(message.getStudentName());
        }
        return convertView;
    }


    class ViewHolder {
        @Bind(R.id.head_photo)
        RoundImageView headPhoto;
        @Bind(R.id.Name)
        TextView name;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.msg_content)
        TextView msgContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
