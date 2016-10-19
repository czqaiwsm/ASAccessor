package com.accessories.city.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.TeacherInfo;
import com.accessories.city.utils.ImageLoaderUtil;
import com.accessories.city.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ChooseTeacherAdpter extends BaseAdapter {
    private List<TeacherInfo> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public ChooseTeacherAdpter(Context context, List<TeacherInfo> items) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.choose_teacher_adapter, null);
            holder.headPhoto = (RoundImageView)convertView.findViewById(R.id.head_photo);
            holder.name = (TextView)convertView.findViewById(R.id.Name);
            holder.idCertify = (TextView) convertView.findViewById(R.id.idCertify);
            holder.eduCertify = (TextView)convertView.findViewById(R.id.eduCertify);
            holder.price = (TextView)convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        TeacherInfo message = mItemList.get(position);
        if(message != null){
            ImageLoader.getInstance().displayImage(message.getHeadImg(), holder.headPhoto, ImageLoaderUtil.mHallIconLoaderOptions);
            holder.name.setText(message.getNickName());
            holder.price.setText(String.format(mContext.getResources().getString(R.string.price), TextUtils.isEmpty(message.getPrice())?"0":message.getPrice()));
            if("1".equalsIgnoreCase(message.getIsIdCardVip())){
                holder.idCertify.setVisibility(View.VISIBLE);
            }else {
                holder.idCertify.setVisibility(View.GONE);
            }

            if("1".equalsIgnoreCase(message.getIsSchoolVip())){
                holder.eduCertify.setVisibility(View.VISIBLE);
            }else {
                holder.eduCertify.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    class ViewHolder {
        private RoundImageView headPhoto;
        private TextView name;
        private TextView idCertify;
        private TextView eduCertify;
        private TextView price;

    }
}
