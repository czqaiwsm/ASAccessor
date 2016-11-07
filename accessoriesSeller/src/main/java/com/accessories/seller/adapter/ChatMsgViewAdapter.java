package com.accessories.seller.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.seller.R;
import com.accessories.seller.bean.ChatMsgEntity;
import com.accessories.seller.utils.BaseApplication;
import com.accessories.seller.utils.ImageLoaderUtil;
import com.accessories.seller.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class ChatMsgViewAdapter extends BaseAdapter {

    public enum  IMsgViewType {
         IMVT_COM_MSG,
         IMVT_TO_MSG
    }

    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();

    private List<ChatMsgEntity> coll;

    private Context ctx;

    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll) {
        ctx = context;
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public IMsgViewType getItemViewType(ChatMsgEntity entity) {
        // TODO Auto-generated method stub
        //聊天方向	是	Int	聊天方向：1-左(对方)，2-右(自己)
        if (TextUtils.equals(entity.getDirection(), "1")) {
            return IMsgViewType.IMVT_COM_MSG;
        } else {
            return IMsgViewType.IMVT_TO_MSG;
        }

    }

    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ChatMsgEntity entity = coll.get(position);

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.chatting_msg, null);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(getItemViewType(entity) == IMsgViewType.IMVT_COM_MSG){
            viewHolder.sendMsgLL.setVisibility(View.GONE);
            viewHolder.comMsgLL.setVisibility(View.VISIBLE);
            viewHolder.comtime.setText(entity.getCreateTime());
            viewHolder.comChatcontent.setText(entity.getContent());
//            viewHolder.comUsername.setText(entity.getTeacherName());
            ImageLoader.getInstance().displayImage(entity.getStudentImg(),viewHolder.comhead,ImageLoaderUtil.mHallIconLoaderOptions);
        }else {
            viewHolder.sendMsgLL.setVisibility(View.VISIBLE);
            viewHolder.comMsgLL.setVisibility(View.GONE);
            viewHolder.sendtime.setText(entity.getCreateTime());
            viewHolder.sendcontent.setText(entity.getContent());

            if(BaseApplication.getUserInfo() != null){
//                viewHolder.send_username.setText(BaseApplication.getUserInfo().getNickName());
                ImageLoader.getInstance().displayImage(entity.getTeacherImg(),viewHolder.sendUserHead,ImageLoaderUtil.mHallIconLoaderOptions);
            }

        }

        return convertView;
    }


    class ViewHolder {
        @Bind(R.id.comtime)
        TextView comtime;
        @Bind(R.id.comhead)
        RoundImageView comhead;
        @Bind(R.id.com_chatcontent)
        TextView comChatcontent;
        @Bind(R.id.com_username)
        TextView comUsername;
        @Bind(R.id.comMsgLL)
        LinearLayout comMsgLL;
        @Bind(R.id.sendtime)
        TextView sendtime;
        @Bind(R.id.sendUserHead)
        RoundImageView sendUserHead;
        @Bind(R.id.sendcontent)
        TextView sendcontent;
        @Bind(R.id.send_username)
        TextView send_username;
        @Bind(R.id.sendMsgLL)
        LinearLayout sendMsgLL;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
