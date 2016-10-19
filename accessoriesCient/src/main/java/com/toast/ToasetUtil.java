package com.toast;

import android.content.Context;
import android.text.TextUtils;

/**
 * @desc 自定义Toast
 * @creator caozhiqing
 * @data 2016/4/1
 */
public class ToasetUtil {


    private  SVProgressHUD svProgressHUD ;
    private Context context;
    public ToasetUtil(Context context){
        this.context = context;
        svProgressHUD = new SVProgressHUD(context);
    }

    public void showWithMaskType(){
//        svProgressHUD.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.None);
//        svProgressHUD.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Black);
//        svProgressHUD.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.BlackCancel);
//        svProgressHUD.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Clear);
//        svProgressHUD.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
//        svProgressHUD.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.Gradient);
//        svProgressHUD.showWithMaskType(SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }
    public  void showWithStatus(String msg){
        if(TextUtils.isEmpty(msg)){
            svProgressHUD.showWithStatus(msg);
        }else {
            svProgressHUD.showWithStatus("加载中...");
        }
    }

    public  void showWithStatus(Context context,int resId){

        String msg = context.getResources().getString(resId);
        showWithStatus(msg);
    }

    public void showInfo(String msg){

        if(TextUtils.isEmpty(msg)){
        }else {
            svProgressHUD.showInfoWithStatus(msg, SVProgressHUD.SVProgressHUDMaskType.None);
        }

    }
    public  void showInfo(int resId){
        String msg = context.getResources().getString(resId);
        showInfo(msg);
    }

    public void showSuccess(String msg){
        if(TextUtils.isEmpty(msg)){
            svProgressHUD.showSuccessWithStatus("恭喜，操作成功！", SVProgressHUD.SVProgressHUDMaskType.None);
        }else {
            svProgressHUD.showSuccessWithStatus(msg, SVProgressHUD.SVProgressHUDMaskType.None);
        }
    }
    public  void showSuccess(int resId){
        String msg = context.getResources().getString(resId);
        showSuccess(msg);
    }



    public void showErro(String msg){
        if(TextUtils.isEmpty(msg)){
            svProgressHUD.showErrorWithStatus("操作失败", SVProgressHUD.SVProgressHUDMaskType.None);
        }else {
            svProgressHUD.showErrorWithStatus(msg, SVProgressHUD.SVProgressHUDMaskType.None);
        }
    }
    public  void showErro(int resId){
        String msg = context.getResources().getString(resId);
        showErro(msg);
    }

    public void dismissToast(){
        svProgressHUD.dismissImmediately();
    }
}
