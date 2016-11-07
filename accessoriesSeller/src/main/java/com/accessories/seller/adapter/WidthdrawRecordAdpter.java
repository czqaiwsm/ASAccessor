package com.accessories.seller.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accessories.seller.R;
import com.accessories.seller.bean.MsgInfo;
import com.accessories.seller.bean.WidthdrawRecord;
import com.accessories.seller.utils.ImageFactory;
import com.accessories.seller.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.SoftReference;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WidthdrawRecordAdpter extends MainAdapter {


    public WidthdrawRecordAdpter(SoftReference<Context> context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext.get()).inflate(R.layout.adapter_widthdraw_record, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WidthdrawRecord widthdrawRecord = (WidthdrawRecord) mItemList.get(position);
        if (widthdrawRecord != null) {

            holder.dataTv.setText(widthdrawRecord.getTime());
            holder.integralTv.setText(widthdrawRecord.getCashIntegral()+"积分");

            if("0".equals(widthdrawRecord.getCashType())){
                holder.descTv.setText("支付宝提现");
            }else {
                holder.descTv.setText("微信提现");
            }
            if("0".equals(widthdrawRecord.getCashStatus())){//0处理中 1已完成 2拒绝
                holder.statusTv.setTextColor(mContext.get().getResources().getColor(R.color.color_ff5a00));
                holder.statusTv.setText("提现中");
            }else if ("1".equals(widthdrawRecord.getCashStatus())){
                holder.statusTv.setTextColor(mContext.get().getResources().getColor(R.color.color_00ba00));
                holder.statusTv.setText("提现成功");
            }else {
                holder.statusTv.setTextColor(mContext.get().getResources().getColor(R.color.color_fe0000));

                holder.statusTv.setText("提现失败");

            }


        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.dataTv)
        TextView dataTv;
        @Bind(R.id.integralTv)
        TextView integralTv;
        @Bind(R.id.descTv)
        TextView descTv;
        @Bind(R.id.statusTv)
        TextView statusTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
