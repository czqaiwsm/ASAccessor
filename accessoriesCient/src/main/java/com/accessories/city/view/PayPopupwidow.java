package com.accessories.city.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.accessories.city.R;
import com.accessories.city.bean.PayInfo;
import com.accessories.city.utils.BaseApplication;

/**
 * @desc 请用一句话描述此文件
 * @creator caozhiqing
 * @data 2016/1/13
 */
public class PayPopupwidow implements View.OnClickListener{

    private Activity activity;
    private  PopupWindow mSortPop;
    private Handler handler;
    private View view;
    private PayInfo payInfo;
    private View.OnClickListener onClickListener;
//    private PayCallBack payCallBack ;

    public PayPopupwidow(Activity activit, View.OnClickListener onClickListener,Object payCallBack){
//        this.payCallBack = payCallBack;
        this.onClickListener = onClickListener;
        this.activity = activit;
        setmSortPop();
//        mSortPop.showAtLocation(getView(), Gravity.BOTTOM,0,0);
    }


    public void payPopShow(View view,PayInfo news){
        if(view == null){
            view = this.view;
        }
        this.payInfo = news;
        if(mSortPop != null){
//            if(BaseApplication.getUserInfo() != null){
//                if(payInfo != null && payInfo.getPrice().compareTo(BaseApplication.getUserInfo().getBalance())>1){
//                    view.findViewById(R.id.wxPay).setVisibility(View.GONE);
//                }
//            }
            mSortPop.showAtLocation(view, Gravity.BOTTOM,0,0);
        }else {
            setmSortPop();
            payPopShow(view,news);
        }
    }



    private void setmSortPop(){
        LayoutInflater inflater = LayoutInflater.from(activity);
        // 引入窗口配置文件
        final View view = inflater
                .inflate(R.layout.pay_selector, null);
        // 创建PopupWindow对象
        mSortPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                activity.getResources().getDimensionPixelSize(R.dimen._150dp), false);
        mSortPop.setAnimationStyle(R.style.popwin_anim_bottom_style);
        // 需要设置一下此参数，点击外边可消失
        mSortPop.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        mSortPop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        mSortPop.setFocusable(true);

        view.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSortPop.dismiss();
            }
        });

        if(onClickListener == null){
            onClickListener = this;
        }
        view.findViewById(R.id.alipay).setOnClickListener(onClickListener);
        view.findViewById(R.id.wxPay).setOnClickListener(onClickListener);
        if(BaseApplication.getUserInfo() != null){
//            if(payInfo != null && payInfo.getPrice().compareTo(BaseApplication.getUserInfo().getBalance())>1){
//                view.findViewById(R.id.wxPay).setVisibility(View.GONE);
//            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alipay://支付宝支付
//                PayUtil.alipay(activity,payInfo,payCallBack);
                break;
            case R.id.wxPay://微信支付
//                PayUtil.walletPay(activity,payInfo,payCallBack);

                break;
        }

        if(mSortPop != null && mSortPop.isShowing()){
            mSortPop.dismiss();
        }
    }

    public void dimiss(){

        if(mSortPop != null && mSortPop.isShowing()){
            mSortPop.dismiss();
        }

    }

    public static void main(String arg[]){

        System.out.println("4".compareTo("10"));
        System.out.println("8".compareTo("10"));
        System.out.println("11".compareTo("10"));

    }

}
