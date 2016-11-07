package com.accessories.city.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtils {
	private static final String SP_FILE_NAME = "hylwash";
	private static final String IS_FISRT = "is_first";
	private static SharePreferenceUtils sharePreferenceUtils = null;
	private SharedPreferences mSharePreference = null;
	private Context mContext = null;

	private static final String AREA_ID = "area_id";
	private static final String AREA_NAME = "area_name";
	private static final String AREA_PARENTID = "area_parentid";

	public static SharePreferenceUtils getInstance(Context context) {
		if (sharePreferenceUtils == null) {
			sharePreferenceUtils = new SharePreferenceUtils(
					context.getApplicationContext());
		}
		return sharePreferenceUtils;
	}

	private SharePreferenceUtils(Context context) {
		mContext = context;
		mSharePreference = mContext.getSharedPreferences(SP_FILE_NAME,
				Context.MODE_WORLD_WRITEABLE);
	}



	public Boolean isFirst() {
		if (mSharePreference != null) {
			Boolean isDisplay = mSharePreference.getBoolean(IS_FISRT, true);
			return isDisplay;
		}
		return null;
	}

	public void saveIsFirst(Boolean isDisplay) {
		if (mSharePreference != null && isDisplay != null) {
			Editor editor = mSharePreference.edit();
			editor.putBoolean(IS_FISRT, isDisplay);
			editor.commit();
		}
	}


	public synchronized void clearArea() {
		if (mSharePreference != null) {
			Editor editor = mSharePreference.edit();
			editor.remove(AREA_ID);
			editor.remove(AREA_NAME);
			editor.remove(AREA_PARENTID);
			editor.commit();
		}
	}

	public synchronized void saveCityName(String str){
		if (mSharePreference != null ) {
			Editor editor = mSharePreference.edit();
			editor.putString("cityName", str);
			editor.commit();
		}
	}

	public synchronized String getCityName() {
		String name = "";
		if (mSharePreference != null) {
			name = mSharePreference.getString("cityName", null);
		}
		return name;
	}

}
