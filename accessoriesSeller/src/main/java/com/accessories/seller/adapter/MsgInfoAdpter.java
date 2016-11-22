package com.accessories.seller.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accessories.seller.R;
import com.accessories.seller.bean.MsgInfo;
import com.accessories.seller.utils.ImageLoaderUtil;
import com.accessories.seller.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.SoftReference;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MsgInfoAdpter extends MainAdapter {


    public MsgInfoAdpter(SoftReference<Context> context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext.get()).inflate(R.layout.adapter_msg_info, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MsgInfo msgInfo = (MsgInfo) mItemList.get(position);
        if(msgInfo != null){
            ImageLoader.getInstance().displayImage(msgInfo.getUserHead(), holder.headPhoto);
            holder.nameTv.setText(msgInfo.getNickname());
            holder.telTv.setText(Html.fromHtml("Tel:<font color='#799cc2'>"+msgInfo.getPhone()+"</font>"));
            holder.telTv.setTag(msgInfo.getPhone());
            holder.telTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //用intent启动拨打电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+v.getTag()));
                    mContext.get().startActivity(intent);
                }
            });
//            danyijiangnan
            if(msgInfo.getPicAry() != null && msgInfo.getPicAry().size()==1){
                holder.oneImg.setVisibility(View.VISIBLE);
                holder.twoImgLl.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage(msgInfo.getPicAry().get(0),holder.oneImg);
            }else {
                holder.oneImg.setVisibility(View.GONE);
                holder.twoImgLl.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(msgInfo.getPicAry().get(0),holder.img1);
                ImageLoader.getInstance().displayImage(msgInfo.getPicAry().get(1),holder.img2);
            }
            if("1".equals(msgInfo.getMsgType())){
                holder.typeTv.setText("求购");
            }else {
                holder.typeTv.setText("供应");
            }

            holder.contentTv.setText(msgInfo.getContent());
            holder.timeTv.setText(msgInfo.getTime());
            holder.addressTv.setText(msgInfo.getAddress());

        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.head_photo)
        RoundImageView headPhoto;
        @Bind(R.id.nameTv)
        TextView nameTv;
        @Bind(R.id.telTv)
        TextView telTv;
        @Bind(R.id.contentTv)
        TextView contentTv;
        @Bind(R.id.oneImg)
        ImageView oneImg;
        @Bind(R.id.img1)
        ImageView img1;
        @Bind(R.id.img2)
        ImageView img2;
        @Bind(R.id.twoImgLl)
        LinearLayout twoImgLl;
        @Bind(R.id.timeTv)
        TextView timeTv;
        @Bind(R.id.addressTv)
        TextView addressTv;
        @Bind(R.id.typeTv)
        TextView typeTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
