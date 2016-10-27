/* 
 * @(#)AlertDialogUtils.java    Created on 2011-6-2
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id: AlertDialogUtils.java 36102 2013-03-20 04:33:56Z xuan $
 */
package com.accessories.city.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import com.accessories.city.R;

/**
 * AlertDialog工具类
 *
 * @author xuan
 * @version $Revision: 36102 $, $Date: 2013-03-20 12:33:56 +0800 (Wed, 20 Mar
 *          2013) $
 */
public class AlertDialogUtils {

    /**
     * 展现简单的一个按钮的alert框，类似网页alert
     *
     * @param title
     * @param message
     * @param buttonText
     */
    public static void displayAlert(Context context, String title, String message, String buttonText) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setPositiveButton(buttonText, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setTitle(title).setMessage(message).create();

        alertDialog.show();
    }

    /**
     * 展现简单的一个按钮的alert框，类似网页alert(可在线程中使用)
     *
     * @param context
     * @param title
     * @param message
     * @param buttonText
     * @param handler
     */
    public static void displayAlert2Handler(final Context context, final String title, final String message, final String buttonText, Handler handler) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                displayAlert(context, title, message, buttonText);
            }
        });
    }

    /**
     * 供用户选择，然后触发事件的提示框
     *
     * @param context
     * @param title           标题
     * @param message         提示文本
     * @param positiveBtnText 确定按钮文本
     * @param positionOnclick 确定按钮事件
     * @param negativeBtnText 取消按钮文本
     * @param negativeOnclick 取消按钮事件
     * @return
     */
    public static Dialog displayMyAlertChoice(Context context, String title, String message, String positiveBtnText, final View.OnClickListener positionOnclick, String negativeBtnText,
                                              View.OnClickListener negativeOnclick) {

        final Dialog builder = new Dialog(context, R.style.MyDialog);
        builder.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_style, null);
        builder.setContentView(view);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_content);
        Button mConfirm = (Button) view.findViewById(R.id.dialog_confirm);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);
        mTitle.setText(title);
        mContent.setText(message);

        mConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                builder.dismiss();
                if (null != positionOnclick) {
                    positionOnclick.onClick(v);
                }

            }
        });
        if (null == negativeOnclick) {
            negativeOnclick = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    builder.dismiss();
                }
            };
        }
        mCancel.setOnClickListener(negativeOnclick);
        Window dialogWindow = builder.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        lp.width = URLConstants.SCREENW / 5 * 4;
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        return builder;
    }

    /**
     * 供用户选择，然后触发事件的提示框
     *
     * @param context
     * @param title           标题
     * @param message         提示文本
     * @param positiveBtnText 确定按钮文本
     * @param positionOnclick 确定按钮事件
     * @param negativeBtnText 取消按钮文本
     * @param negativeOnclick 取消按钮事件
     * @return
     */
    public static Dialog displayMyAlertChoice(Context context, int title, int message, int positiveBtnText, final View.OnClickListener positionOnclick, final int negativeBtnText,
                                             final View.OnClickListener negativeOnclick) {

        final Dialog builder = new Dialog(context, R.style.MyDialog);
        builder.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_style, null);
        builder.setContentView(view);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_content);
        Button mConfirm = (Button) view.findViewById(R.id.dialog_confirm);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);
        mTitle.setText(title);
        mContent.setText(message);
        mConfirm.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                builder.dismiss();
                if(positionOnclick != null){
                    positionOnclick.onClick(v);
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                builder.dismiss();
                if(negativeOnclick != null){
                    negativeOnclick.onClick(v);
                }
            }
        });
        Window dialogWindow = builder.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        lp.width =URLConstants.SCREENW / 5 * 4;
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        return builder;
    }

    /**
     *
     * @param context
     * @param title
     * @param message
     * @param positionOnclick
     * @param negativeOnclick
     * @return
     */
    public static Dialog displayMyAlertChoice(Context context, String title, String message,  final View.OnClickListener positionOnclick,
                                             final View.OnClickListener negativeOnclick) {

        final Dialog builder = new Dialog(context, R.style.MyDialog);
        builder.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_style, null);
        builder.setContentView(view);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_content);
        Button mConfirm = (Button) view.findViewById(R.id.dialog_confirm);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);
        mTitle.setText(title);
        mContent.setText(message);
        mConfirm.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                builder.dismiss();
                if(positionOnclick != null){
                    positionOnclick.onClick(v);
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                builder.dismiss();
                if(negativeOnclick != null){
                    negativeOnclick.onClick(v);
                }
            }
        });
        Window dialogWindow = builder.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        lp.width =URLConstants.SCREENW / 5 * 4;
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        builder.show();
        return builder;
    }

    /**
     * 供用户选择，然后触发事件的提示框
     *
     * @param context
     * @param title           标题
     * @param message         提示文本
     * @param positionOnclick 确定按钮事件
     * @return
     */
    public static void displayMyAlertChoice(final Context context, String title, String message, final View.OnClickListener positionOnclick) {

        final Dialog builder = new Dialog(context, R.style.MyDialog);
        builder.setCancelable(true);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_style_confirm, null);
        builder.setContentView(view);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_content);
        Button mConfirm = (Button) view.findViewById(R.id.dialog_confirm);
        mTitle.setText(title);
        mContent.setText(message);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                if(positionOnclick != null){
                    positionOnclick.onClick(v);
                }
            }
        });
        Window dialogWindow = builder.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        lp.width =URLConstants.SCREENW / 5 * 4;
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        builder.show();
    }

    /**
     * 多个选择选一个
     *
     * @param context
     * @param title
     * @param cancelable
     * @param selectNames
     */
    public static void displayAlert4SingleChoice(Context context, String title, boolean cancelable, String[] selectNames, final OnClickListener onClickListener) {
        AlertDialog accountDlg = new AlertDialog.Builder(context).setTitle(title).setCancelable(cancelable).setSingleChoiceItems(selectNames, -1, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (null != onClickListener) {
                    onClickListener.onClick(dialog, which);
                }

                dialog.dismiss();
            }
        }).create();
        accountDlg.show();
    }

    public static Dialog displayMyAlertChoice(Context context, final Dialog builder, int title, int message, int positiveBtnText, final View.OnClickListener positionOnclick, final int negativeBtnText,
                                              final   View.OnClickListener negativeOnclick) {

        // final Dialog builder = new Dialog(context, R.style.MyDialog);
        builder.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_style, null);
        builder.setContentView(view);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_content);
        Button mConfirm = (Button) view.findViewById(R.id.dialog_confirm);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);
        mTitle.setText(title);
        mContent.setText(message);
        mConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                builder.dismiss();
                if(positionOnclick != null){
                    positionOnclick.onClick(v);
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                builder.dismiss();
                if(negativeOnclick != null){
                  negativeOnclick.onClick(v);
                }
            }
        });
        Window dialogWindow = builder.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        lp.width =URLConstants.SCREENW / 5 * 4;
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        return builder;
    }


    /**
     *
     * @param context
     * @param title
     * @param message
     * @param positionOnclick
     * @return
     */
    public static Dialog displaySingle(Context context, String title, String message,  final View.OnClickListener positionOnclick) {

        final Dialog builder = new Dialog(context, R.style.MyDialog);
        builder.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_style, null);
        builder.setContentView(view);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_content);
        Button mConfirm = (Button) view.findViewById(R.id.dialog_confirm);
        view.findViewById(R.id.dialog_cancel).setVisibility(View.GONE);
        view.findViewById(R.id.cutLine).setVisibility(View.GONE);

        mTitle.setText(title);
        mContent.setText(message);
        mConfirm.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                builder.dismiss();
                if(positionOnclick != null){
                    positionOnclick.onClick(v);
                }
            }
        });
        Window dialogWindow = builder.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        lp.width =URLConstants.SCREENW / 5 * 4;
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        builder.show();
        return builder;
    }
}
