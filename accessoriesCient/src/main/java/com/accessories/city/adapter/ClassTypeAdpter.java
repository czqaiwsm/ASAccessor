package com.accessories.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.city.R;
import com.accessories.city.bean.CateSubTypeEntity;
import com.accessories.city.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ClassTypeAdpter extends BaseAdapter {
    private List<CateSubTypeEntity> mItemList;
    private Context mContext;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public ClassTypeAdpter(Context context, List<CateSubTypeEntity> items) {
        this.mContext = context;
        this.mItemList = items;
    }

    @Override
    public CateSubTypeEntity getItem(int arg0) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.classe_tyep_adapter, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        
        CateSubTypeEntity type=mItemList.get(position);
        holder.type_name.setText(type.getName());
        ImageLoader.getInstance().displayImage(type.getPicUrl(), holder.type_img, ImageLoaderUtil.mHallIconLoaderOptions);
        
        return convertView;
    }

    class ViewHolder {
    	@Bind(R.id.type_img)
    	ImageView type_img;
    	@Bind(R.id.type_name)
        TextView type_name;
    	@Bind(R.id.content)
        TextView content;
        ViewHolder(View view){
        	 ButterKnife.bind(this, view);
        }
    }
}
