package io.jchat.android.controller;

import android.view.View;
import android.view.View.OnClickListener;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import com.accessories.city.R;
import io.jchat.android.activity.MeInfoActivity;
import io.jchat.android.view.MeInfoView;

public class MeInfoController implements OnClickListener {

    private MeInfoView mMeInfoView;
    private MeInfoActivity mContext;


    public MeInfoController(MeInfoView view, MeInfoActivity context) {
        this.mMeInfoView = view;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.return_btn) {
            mContext.setResultAndFinish();

        } else if (i == R.id.nick_name_rl) {
            mContext.startModifyNickNameActivity();

        } else if (i == R.id.sex_rl) {
            UserInfo userInfo = JMessageClient.getMyInfo();
            UserInfo.Gender gender = userInfo.getGender();
            mContext.showSexDialog(gender);

        } else if (i == R.id.location_rl) {
            mContext.startSelectAreaActivity();

        } else if (i == R.id.sign_rl) {
            mContext.startModifySignatureActivity();

        }
    }

}
