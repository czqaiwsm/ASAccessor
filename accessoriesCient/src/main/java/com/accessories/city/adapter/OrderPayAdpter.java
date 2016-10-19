package com.accessories.city.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.city.R;
import com.accessories.city.bean.OrderInfo;
import com.accessories.city.utils.SmartToast;

import java.util.List;

public class OrderPayAdpter extends BaseAdapter {
    private List<OrderInfo> mItemList;
    private Context mContext;
    private int flag = 0 ;//1 待支付\2 已支付\ 3 已取消\ 4 已完成

    private View.OnClickListener listener;
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();

    }

    public OrderPayAdpter(Context context, List<OrderInfo> items, int flag, View.OnClickListener listener) {
        this.mContext = context;
        this.mItemList = items;
        this.flag = flag;
        this.listener = listener;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.order_pay_adapter, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.left_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartToast.showText(mContext, "onClick");
            }
        });
        OrderInfo message = mItemList.get(position);
        if (message != null) {
            holder.name.setText(message.getStudentName()
            );
            holder.courseName.setText(message.getCourseName());
            String price = message.getPayPrice();
            if(!TextUtils.isEmpty(price)){
                holder.price.setText(String.format(mContext.getResources().getString(R.string.balance_has,message.getPayPrice())));
            }
            holder.timeDetail.setText(TextUtils.isEmpty(message.getPayPrice())?"":message.getPayTime());

            holder.left_tv.setVisibility(View.GONE);
            holder.right_tv.setVisibility(View.GONE);

            holder.left_tv.setOnClickListener(listener);
            holder.right_tv.setOnClickListener(listener);


            holder.left_tv.setTag(position);
            holder.right_tv.setTag(position);

            String statuStr = "";
            switch (flag){
                case 1:
                    statuStr = "待支付";
                    holder.left_tv.setVisibility(View.VISIBLE);
                    holder.right_tv.setVisibility(View.VISIBLE);
                    holder.left_tv.setText(mContext.getResources().getString(R.string.contact_tea));
                    holder.right_tv.setText(mContext.getResources().getString(R.string.pay_now));
                    break;
                case 2:
                    statuStr = "已支付";
                    holder.right_tv.setVisibility(View.VISIBLE);
                    holder.left_tv.setVisibility(View.VISIBLE);
                    holder.right_tv.setText(mContext.getResources().getString(R.string.feed_money));
                    holder.left_tv.setText("完成订单");
                    break;
                case 3:
                    break;
                case 4:
                    statuStr = "已完成";
                    holder.right_tv.setText(mContext.getResources().getString(R.string.assert_now));

                       if(TextUtils.equals(message.getEvaluateStatus(),"0")){
                           holder.right_tv.setVisibility(View.VISIBLE);
                       }else {
                           holder.right_tv.setVisibility(View.GONE);
                       }
                    break;
            }
            holder.payStatus.setText(statuStr);


            holder.left_tv.setVisibility(View.GONE);
            holder.right_tv.setVisibility(View.GONE);
        }
        return convertView;
    }



    class ViewHolder {
        @Bind(R.id.name)
        TextView name;//姓名
        @Bind(R.id.payStatus)
        TextView payStatus;//
        @Bind(R.id.courseName)
        TextView courseName;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.view)
        View view;
        @Bind(R.id.timeDetail)
        TextView timeDetail;
        @Bind(R.id.left_tv)
        TextView left_tv;
        @Bind(R.id.right_tv)
        TextView right_tv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
