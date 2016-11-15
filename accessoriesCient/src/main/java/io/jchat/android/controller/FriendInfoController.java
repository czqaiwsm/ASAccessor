package io.jchat.android.controller;


import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import com.accessories.city.R;
import io.jchat.android.activity.FriendInfoActivity;
import io.jchat.android.application.JChatDemoApplication;
import io.jchat.android.chatting.utils.DialogCreator;
import io.jchat.android.chatting.utils.HandleResponseCode;
import io.jchat.android.view.FriendInfoView;
import io.jchat.android.view.SlipButton;

public class FriendInfoController implements OnClickListener, SlipButton.OnChangedListener {

    private FriendInfoView mFriendInfoView;
    private FriendInfoActivity mContext;


    public FriendInfoController(FriendInfoView view, FriendInfoActivity context) {
        this.mFriendInfoView = view;
        this.mContext = context;
        initData();
    }

    private void initData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.friend_info_return_btn) {
            Intent intent = new Intent();
            String nickname = mContext.getNickname();
            intent.putExtra(JChatDemoApplication.NICKNAME, nickname);
            mContext.setResult(JChatDemoApplication.RESULT_CODE_FRIEND_INFO, intent);
            mContext.finish();

        } else if (i == R.id.friend_send_msg_btn) {
            mContext.startChatActivity();

        } else if (i == R.id.friend_detail_avatar) {
            mContext.startBrowserAvatar();

        } else if (i == R.id.name_rl) {//                Intent intent = new Intent();
//                intent.setClass(mContext, EditNoteNameActivity.class);
//                intent.putExtra("noteName", "ddsklf");
//                intent.putExtra("friendDescription", "kjdkjdlkj");
//                mContext.startActivity(intent);

        }
    }

    @Override
    public void onChanged(int id, final boolean flag) {
        final Dialog dialog = DialogCreator.createLoadingDialog(mContext, mContext.getString(R.string.jmui_loading));
        if (id == R.id.black_list_slip_btn) {
            List<String> list = new ArrayList<String>();
            list.add(mContext.getUserName());
            dialog.show();
            if (flag) {
                JMessageClient.addUsersToBlacklist(list, mContext.getTargetAppKey(), new BasicCallback() {
                    @Override
                    public void gotResult(int status, String desc) {
                        dialog.dismiss();
                        if (status == 0) {
                            Log.d("FriendInfoController", "add user to black list success!");
                            Toast.makeText(mContext,
                                    mContext.getString(R.string.add_to_blacklist_success_hint),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mFriendInfoView.setBlackBtnChecked(false);
                            HandleResponseCode.onHandle(mContext, status, false);
                        }
                    }
                });
            } else {
                JMessageClient.delUsersFromBlacklist(list, mContext.getTargetAppKey(), new BasicCallback() {
                    @Override
                    public void gotResult(int status, String desc) {
                        dialog.dismiss();
                        if (status == 0) {
                            Toast.makeText(mContext,
                                    mContext.getString(R.string.remove_from_blacklist_hint),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mFriendInfoView.setBlackBtnChecked(true);
                            HandleResponseCode.onHandle(mContext, status, false);
                        }
                    }
                });
            }

            //滑动免打扰按钮是否加入免打扰,userInfo.setNoDisturb(int flag, BasicCallback callback)
            //flag为1表示加入免打扰,否则为0
        } else if (id == R.id.no_disturb_slip_btn) {
            dialog.show();
            mContext.getUserInfo().setNoDisturb(flag ? 1 : 0, new BasicCallback() {
                @Override
                public void gotResult(int status, String desc) {
                    dialog.dismiss();
                    if (status == 0) {
                        if (flag) {
                            Toast.makeText(mContext, mContext
                                            .getString(R.string.set_do_not_disturb_success_hint),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, mContext
                                            .getString(R.string.remove_from_no_disturb_list_hint),
                                    Toast.LENGTH_SHORT).show();
                        }
                        //设置失败恢复原来的状态
                    } else {
                        if (flag) {
                            mFriendInfoView.setNoDisturbChecked(false);
                        } else {
                            mFriendInfoView.setNoDisturbChecked(true);
                        }
                        HandleResponseCode.onHandle(mContext, status, false);
                    }
                }
            });

        }
    }
}
