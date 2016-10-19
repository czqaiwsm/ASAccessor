package com.accessories.city.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @desc 获取屏幕高度，宽度
 * @creator caozhiqing
 * @data 2015/11/26
 */
public class ScreenUitlity {


    /**
     * 获取屏幕宽、高
     * @param context
     * @return 数组{width，heigth}
     */
    public static int[] getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWm.getDefaultDisplay().getMetrics(dm);
//        dm.density;
        int size[] = {dm.widthPixels,dm.heightPixels,};
        return size;
    }

    public static int dip2px(Context context, float dpValue) {
//        dp = px / (ppi / 160)
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
