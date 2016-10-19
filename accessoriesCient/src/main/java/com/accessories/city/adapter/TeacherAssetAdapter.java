package com.accessories.city.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.CommentInfo;
import com.accessories.city.utils.ImageLoaderUtil;
import com.accessories.city.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 老师-->评价
 */

public class TeacherAssetAdapter extends BaseAdapter {
    private List<CommentInfo> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();
    }

    public TeacherAssetAdapter(Context context, List<CommentInfo> items) {
        this.mContext = context;
        this.mItemList = items;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.teacher_asset_adapter,null);
            holder.headPhoto = (RoundImageView)convertView.findViewById(R.id.head_photo);
            holder.name = (TextView) convertView.findViewById(R.id.Name);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.assetConten = (TextView) convertView.findViewById(R.id.msg_content);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.myRatingbar);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommentInfo map = mItemList.get(position);
        if(map != null){
            ImageLoader.getInstance().displayImage(map.getHeadImg(),holder.headPhoto, ImageLoaderUtil.mHallIconLoaderOptions);
            holder.name.setText(map.getStudentName());
            holder.time.setText(map.getCommentTime());
            holder.assetConten.setText(map.getCommentDesc());
            holder.ratingBar.setRating(Float.valueOf(map.getServiceScore()));
        }
        return convertView;
    }

    class ViewHolder {
        private RoundImageView headPhoto;
        private TextView name;
        private TextView time;
        private TextView assetConten;//评价类容
        private RatingBar ratingBar ;//
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
