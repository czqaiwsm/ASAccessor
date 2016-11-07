package com.accessories.seller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.seller.R;
import com.accessories.seller.bean.CateSubTypeEntity;
import com.accessories.seller.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.SoftReference;
import java.util.List;

public class ClassTypeAdpter extends MainAdapter {

    public ClassTypeAdpter(SoftReference<Context>  context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext.get()).inflate(R.layout.classe_tyep_adapter, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        
        CateSubTypeEntity type=(CateSubTypeEntity) mItemList.get(position);
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
