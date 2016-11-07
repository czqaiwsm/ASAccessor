package com.accessories.city.utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/7/31
 */
class EncryptPhoneStateListener extends PhoneStateListener {
    TelephonyManager telephonyManager = null;

    EncryptPhoneStateListener(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_OFFHOOK:// 拨打电话或者接听电话时
               Log.i(">>","state = CALL_STATE_OFFHOOK");
                break;
            case TelephonyManager.CALL_STATE_RINGING:// 电话进来时
                Log.i(">>","state = CALL_STATE_RINGING");
                break;
            case TelephonyManager.CALL_STATE_IDLE:// 挂起电话时候，或者没有任何反映
                Log.i(">>","state = CALL_STATE_IDLE");
                break;
            default:
                break;
        }
    }
}
