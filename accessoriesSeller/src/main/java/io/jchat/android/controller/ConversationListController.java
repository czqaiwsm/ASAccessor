package io.jchat.android.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.accessories.seller.R;
import com.accessories.seller.utils.URLConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import io.jchat.android.activity.ConversationListFragment;
import io.jchat.android.adapter.ConversationListAdapter;
import io.jchat.android.application.JChatDemoApplication;
import io.jchat.android.chatting.ChatActivity;
import io.jchat.android.chatting.utils.DialogCreator;
import io.jchat.android.chatting.utils.HandleResponseCode;
import io.jchat.android.tools.SortConvList;
import io.jchat.android.view.ConversationListView;

public class ConversationListController implements OnClickListener,
        OnItemClickListener, OnItemLongClickListener {

    private ConversationListView mConvListView;
    private ConversationListFragment mContext;
    private List<Conversation> mDatas = new ArrayList<Conversation>();
    private ConversationListAdapter mListAdapter;
    private int mWidth;
    private Dialog mDialog,mAddFriendDialog,mLoadingDialog;


    public ConversationListController(ConversationListView listView, ConversationListFragment context,
                                      int width) {
        this.mConvListView = listView;
        this.mContext = context;
        this.mWidth = width;
        initConvListAdapter();
    }

    // 得到会话列表
    private void initConvListAdapter() {
        if(mDatas == null) mDatas = new ArrayList<Conversation>();
           mDatas.clear();
           List temp = JMessageClient.getConversationList();
           mDatas.addAll(temp != null?temp:new ArrayList<Conversation>());
        //对会话列表进行时间排序
        if (mDatas.size() > 1) {
            SortConvList sortList = new SortConvList();
            Collections.sort(mDatas, sortList);
        }

        mListAdapter = new ConversationListAdapter(mContext.getActivity(), mDatas);
        mConvListView.setConvListAdapter(mListAdapter);
    }

    private boolean isExistConv(String targetId) {
        Conversation conv = JMessageClient.getSingleConversation(targetId);
        return conv != null;
    }

    public void dismissSoftInput() {
        InputMethodManager imm = ((InputMethodManager) mContext.getActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (mContext.getActivity().getWindow().getAttributes().softInputMode
                != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (mContext.getActivity().getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(mContext.getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void getUserInfo(final String targetId){
        JMessageClient.getUserInfo(targetId, URLConstants.CONVERSATION_IM_APPKEY , new GetUserInfoCallback() {
            @Override
            public void gotResult(final int status, String desc, final UserInfo userInfo) {
                mLoadingDialog.dismiss();
                if (status == 0) {
                    Conversation conv = Conversation.createSingleConversation(targetId,URLConstants.CONVERSATION_IM_APPKEY);
                    if (!TextUtils.isEmpty(userInfo.getAvatar())) {
                        userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                            @Override
                            public void gotResult(int status, String desc, Bitmap bitmap) {
                                if (status == 0) {
                                   getAdapter().notifyDataSetChanged();
                                } else {
                                    HandleResponseCode.onHandle(mContext.getActivity(), status, false);
                                }
                            }
                        });
                    }
                    getAdapter().setToTop(conv);
                    mAddFriendDialog.cancel();
                } else {
                    HandleResponseCode.onHandle(mContext.getActivity(), status, true);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.create_group_btn) {
//            mContext.showMenuPopWindow();

            mAddFriendDialog = new Dialog(mContext.getActivity(), R.style.jmui_default_dialog_style);
            final View view = LayoutInflater.from(mContext.getActivity())
                    .inflate(R.layout.dialog_add_friend_to_conv_list, null);
            mAddFriendDialog.setContentView(view);
            mAddFriendDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
            mAddFriendDialog.show();
            final EditText userNameEt = (EditText) view.findViewById(R.id.user_name_et);
            final Button cancel = (Button) view.findViewById(R.id.jmui_cancel_btn);
            final Button commit = (Button) view.findViewById(R.id.jmui_commit_btn);
            OnClickListener listener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = view.getId();
                    if (i == R.id.jmui_cancel_btn) {
                        mAddFriendDialog.cancel();

                    } else if (i == R.id.jmui_commit_btn) {
                        String targetId = userNameEt.getText().toString().trim();
                        Log.i("MenuItemController", "targetID " + targetId);
                        if (TextUtils.isEmpty(targetId)) {
                            HandleResponseCode.onHandle(mContext.getContext(), 801001, true);
                        } else if (targetId.equals(JMessageClient.getMyInfo().getUserName())
                                || targetId.equals(JMessageClient.getMyInfo().getNickname())) {
                            Toast.makeText(mContext.getActivity(),
                                    mContext.getString(R.string.user_add_self_toast),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (isExistConv(targetId)) {
                            Toast.makeText(mContext.getActivity(),
                                    mContext.getString(R.string.jmui_user_already_exist_toast),
                                    Toast.LENGTH_SHORT).show();
                            userNameEt.setText("");
                        } else {
                            mLoadingDialog = DialogCreator.createLoadingDialog(mContext.getActivity(),
                                    mContext.getString(R.string.adding_hint));
                            mLoadingDialog.show();
                            dismissSoftInput();
                            getUserInfo(targetId);
                        }

                    }
                }
            };
            cancel.setOnClickListener(listener);
            commit.setOnClickListener(listener);

        }
    }

    // 点击会话列表
    @Override
    public void onItemClick(AdapterView<?> viewAdapter, View view, int position, long id) {
        // TODO Auto-generated method stub
        final Intent intent = new Intent();
        if (position > 0) {
            Conversation conv = mDatas.get(position - 1);
            if (null != conv) {
                // 当前点击的会话是否为群组
                if (conv.getType() == ConversationType.group) {
                    long groupId = ((GroupInfo) conv.getTargetInfo()).getGroupID();
                    intent.putExtra(JChatDemoApplication.GROUP_ID, groupId);
                    intent.putExtra(JChatDemoApplication.DRAFT, getAdapter().getDraft(conv.getId()));
                    intent.setClass(mContext.getActivity(), ChatActivity.class);
                    mContext.getActivity().startActivity(intent);
                    return;
                } else {
                    String targetId = ((UserInfo) conv.getTargetInfo()).getUserName();
                    intent.putExtra(JChatDemoApplication.TARGET_ID, targetId);
                    intent.putExtra(JChatDemoApplication.TARGET_APP_KEY, conv.getTargetAppKey());
                    Log.d("ConversationList", "Target app key from conversation: " + conv.getTargetAppKey());
                    intent.putExtra(JChatDemoApplication.DRAFT, getAdapter().getDraft(conv.getId()));
                }
                intent.setClass(mContext.getActivity(), ChatActivity.class);
                mContext.getActivity().startActivity(intent);
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> viewAdapter, View view, final int position, long id) {
        if (position > 0) {
            final Conversation conv = mDatas.get(position - 1);
            if (conv != null) {
                OnClickListener listener = new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (conv.getType() == ConversationType.group) {
                            JMessageClient.deleteGroupConversation(((GroupInfo) conv.getTargetInfo())
                                    .getGroupID());
                        } else {
                            //使用带AppKey的接口,可以删除跨/非跨应用的会话(如果不是跨应用,conv拿到的AppKey则是默认的)
                            JMessageClient.deleteSingleConversation(((UserInfo) conv.getTargetInfo())
                                    .getUserName(), conv.getTargetAppKey());
                        }
                        mDatas.remove(position - 1);
                        mListAdapter.notifyDataSetChanged();
                        mDialog.dismiss();
                    }
                };
                mDialog = DialogCreator.createDelConversationDialog(mContext.getActivity(), conv.getTitle(),
                        listener);
                mDialog.show();
                mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
            }
        }
        return true;
    }

    public ConversationListAdapter getAdapter() {
        return mListAdapter;
    }

}
