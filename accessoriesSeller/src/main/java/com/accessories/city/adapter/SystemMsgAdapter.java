package com.accessories.city.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.SystemMsg;
import com.accessories.city.fragment.center.SystemMsgFragment;
import com.accessories.city.utils.AlertDialogUtils;

import java.util.List;

public class SystemMsgAdapter extends BaseAdapter {
    private List<SystemMsg> mItemList;
    private Context mContext;
    private View.OnClickListener listener = null;

    private SystemMsgFragment systemMsgFragment = null;
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItemList == null ? 0 : mItemList.size();
    }

    public SystemMsgAdapter(Context context, List<SystemMsg> items,SystemMsgFragment systemMsgFragment) {
        this.mContext = context;
        this.mItemList = items;
        this.systemMsgFragment = systemMsgFragment;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sys_msg_adapter,null);
            holder.msgTitle = (TextView)convertView.findViewById(R.id.msgTitle);
            holder.msgContent = (TextView)convertView.findViewById(R.id.msg_content);
            holder.msgTime = (TextView)convertView.findViewById(R.id.msg_time);
            holder.delMsg = (TextView)convertView.findViewById(R.id.delMsg);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SystemMsg map = mItemList.get(position);
        if(map != null){
            holder.msgTitle.setText(map.getTitle());
            holder.msgContent.setText(map.getContent());
            holder.msgTime.setText(map.getCreateTime());
            holder.delMsg.setTag(map.getId());
        }

        holder.delMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialogUtils.displayMyAlertChoice(mContext, "提示", "您确定删除信息?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            systemMsgFragment.delMsgReq(view.getTag().toString());

                    }
                },null);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView msgTitle;
        private TextView msgContent;
        private TextView msgTime;
        private TextView delMsg;
    }

    @Override
    public Object getItem(int position) {

        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
