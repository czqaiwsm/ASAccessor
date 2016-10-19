package com.accessories.city.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static final String DATE = "yyyy-MM-dd";

	public static final String DATE2 = "yyyy-MM-dd HH:mm:ss";

	public static String dateToString(Date date, String dateStyle) {

		return new SimpleDateFormat(dateStyle).format(date);

	}

	public static Date stringToDate(String dateStyle) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		return sdf.parse(dateStyle);

	}

	/**
	 * ??????????
	 *
	 * @param my_month
	 *
	 * @return
	 */
	public static String getMonthLastMonth(Calendar my_month) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		// my_month.add(Calendar.DATE, 1);// ?????????1??
		my_month.add(Calendar.MONTH, -1);// ?��??1
		// String ss1 = sdf.format(my_month.getTime());

		// my_month.add(Calendar.MONTH, 1);// ????????????????1??
		// my_month.add(Calendar.DATE, -1);// ???????????????????
		String ss2 = sdf.format(my_month.getTime());

		// return ss1 + "," + ss2;
		return ss2;
	}

	/**
	 * ??????????
	 *
	 * @return
	 */
	public static String getMonthNextMonth(Calendar my_month) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		// my_month.set(Calendar.DATE, 1);// ?????????1??
		// my_month.add(Calendar.MONTH, 1);// ?��??1
		// String ss1 = sdf.format(my_month.getTime());

		my_month.add(Calendar.MONTH, 1);// ????????????????1??
		// my_month.add(Calendar.DATE, -1);// ???????????????????
		String ss2 = sdf.format(my_month.getTime());

		// return ss1 + "," + ss2;
		return ss2;
	}

	public static String getTotalPrice(int count, double price) {
		return getPriceValue(count * price);
	}

	/**
	 * ??????????�˧�??
	 *
	 * @param price
	 * @return *.00
	 */
	public static String getPriceValue(double price) {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");// ?????????????????????��??????2��,????0????.
		return decimalFormat.format(price);// format ????????????
	}

	public static void reload(Activity act) {
		Intent intent = act.getIntent();
		act.overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		act.finish();
		act.overridePendingTransition(0, 0);
		act.startActivity(intent);
	}

	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// ?????????????????100?????????????????????????baos??
		int options = 100;
		while (baos.toByteArray().length / 1024 > 50) { // ????��?????????????????100kb,??????????
			baos.reset();// ????baos?????baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ???????options%?????????????????baos??
			options -= 10;// ??��?????10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ????????????baos????ByteArrayInputStream??
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ??ByteArrayInputStream??????????
		return bitmap;
	}

	/*
	 * * ????????????,?????data/data/packageName/cache???????
	 *
	 * @param mContext
	 *
	 * @param packageName
	 */

	public static void clearCache(final Context mContext, final String packageName) {

		(new Thread() {
			@Override
			public void run() {
				try {
					Context otherAppContext = mContext.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
					File path = otherAppContext.getCacheDir();
					if (path == null)
						return;
					// // #rm -r xxx ????????xxx??????��???????????????
					// String killer = " rm -r " + path.toString();
					// Process p = Runtime.getRuntime().exec("su");
					// DataOutputStream os = new
					// DataOutputStream(p.getOutputStream());
					// os.writeBytes(killer.toString() + "\n");
					// os.writeBytes("exit\n");
					// os.flush();
					DataCleanManager.cleanCustomCache(path.getAbsolutePath());
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();

	}

	/**
	 *
	 * @param srcPath
	 */
	public static void compress(String srcPath) {
		float hh = URLConstants.SCREENH;
		float ww =  URLConstants.SCREENW;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, opts);
		opts.inJustDecodeBounds = false;
		int w = opts.outWidth;
		int h = opts.outHeight;
		int size = 0;
		if (w <= ww && h <= hh) {
			size = 1;
		} else {
			double scale = w >= h ? w / ww : h / hh;
			double log = Math.log(scale) / Math.log(2);
			double logCeil = Math.ceil(log);
			size = (int) Math.pow(2, logCeil);
		}
		opts.inSampleSize = size;
		bitmap = BitmapFactory.decodeFile(srcPath, opts);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int quality = 100;
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		while (baos.toByteArray().length > 50 * 1024) {
			baos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			quality -= 20;
		}
		try {
			baos.writeTo(new FileOutputStream(srcPath));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析一个星期是星期几
	 * @param pTime
	 * @return
     */
	public static int dayForWeek(String pTime)  {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayForWeek = 0;
		if(c.get(Calendar.DAY_OF_WEEK) == 1){
			dayForWeek = 7;
		}else{
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}




	public static void main(String args[]) throws ParseException {
        Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		System.out.println(c.get(Calendar.YEAR));
		System.out.println(c.get(Calendar.MONTH));
		System.out.println(c.get(Calendar.DAY_OF_MONTH));


	}

}
