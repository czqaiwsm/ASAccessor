package io.jchat.android.controller;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.accessories.city.R;
import io.jchat.android.activity.MeFragment;
import io.jchat.android.chatting.utils.DialogCreator;
import io.jchat.android.tools.NativeImageLoader;
import io.jchat.android.view.MeView;

public class MeController implements OnClickListener {

    private MeView mMeView;
    private MeFragment mContext;
    private Dialog mDialog;
    private int mWidth;

    public MeController(MeView meView, MeFragment context, int width) {
        this.mMeView = meView;
        this.mContext = context;
        this.mWidth = width;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.my_avatar_iv) {
            Log.i("MeController", "avatar onClick");
            mContext.startBrowserAvatar();

        } else if (i == R.id.take_photo_iv) {
            OnClickListener listener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = v.getId();
                    if (i == R.id.jmui_take_photo_btn) {
                        mDialog.cancel();
                        mContext.takePhoto();

                    } else if (i == R.id.jmui_pick_picture_btn) {
                        mDialog.cancel();
                        mContext.selectImageFromLocal();

                    }
                }
            };
            mDialog = DialogCreator.createSetAvatarDialog(mContext.getActivity(), listener);
            mDialog.show();
            mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);

        } else if (i == R.id.user_info_rl) {
            mContext.startMeInfoActivity();

        } else if (i == R.id.setting_rl) {
            mContext.StartSettingActivity();

//			//退出登录 清除Notification，清除缓存
        } else if (i == R.id.logout_rl) {
            OnClickListener listener;
            listener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = view.getId();
                    if (i == R.id.jmui_cancel_btn) {
                        mDialog.cancel();

                    } else if (i == R.id.jmui_commit_btn) {
                        mContext.Logout();
                        mContext.cancelNotification();
                        NativeImageLoader.getInstance().releaseCache();
                        mContext.getActivity().finish();
                        mDialog.cancel();

                    }
                }
            };
            mDialog = DialogCreator.createLogoutDialog(mContext.getActivity(), listener);
            mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
            mDialog.show();

//		case R.id.birthday:
//			Calendar calendar = Calendar.getInstance();
//			String dateStr = mBirthday.getText().toString().trim();
//			this.mDatePickerDialog = new DatePickerDialog(this.getActivity(), new OnDateSetListener() {
//
//				@Override
//				public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {
//					final String birthday = year + "-" + (month + 1) + "-" + dayOfMonth;
//					mBirthday.setText(birthday);
//					birthdayStr = birthday;
//				}
//			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//			this.mDatePickerDialog.show();
//			break;
//		case R.id.name:
//			mType = ChangeType.NICKNAME;
//			showInput(this.mNameLayout);
//			break;
//		case R.id.sign:
//			mType = ChangeType.SIGNATURE;
//			showInput(this.mSignatureLayout);
//			break;
//		case R.id.location:
//
//			mType = ChangeType.LOCATION;
//			showInput(this.mLocationLayout);
//			break;
//		case R.id.edit_layout:
//			showAllLayout();
//			saveInfo();
//			break;
//		default:
//			break;
        }
    }

}
