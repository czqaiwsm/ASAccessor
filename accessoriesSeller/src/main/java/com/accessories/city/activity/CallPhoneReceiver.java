package com.accessories.city.activity;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/8/1
 */
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;

import java.util.HashMap;
import java.util.Map;


public class CallPhoneReceiver extends BroadcastReceiver {
    private boolean isOutGoingCall = false;
    private boolean isOFFHOOK = false;
    private long time;
    public static Handler mHandler = null;
    final PhoneStateListener listener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i("PhoneReceiver", "CALL IN RINGING :" + incomingNumber);
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    isOFFHOOK = true;
                    Log.i("PhoneReceiver", "CALL IN ACCEPT :" + incomingNumber);
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (isOFFHOOK && isOutGoingCall) {
                        time = (System.currentTimeMillis() - time) / 1000;
                        if (time > 50) {
                            // TODO: 16/8/3  请求接口
                            if(mHandler != null){
                                mHandler.sendEmptyMessage(1);
                            }

                        }
                        isOFFHOOK = false;
                        isOutGoingCall = false;
                    }
                    Log.i("PhoneReceiver", "CALL IDLE");
                    //toddo 挂断后获取
                    break;
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(">>>>", intent.getAction());
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

        tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            isOutGoingCall = true;
            time = System.currentTimeMillis();
            Log.i(">>>>", "外拨电话动作");
            Log.i(">>>>", "isOutGoingCall：" + isOutGoingCall);
        }

    }

}
