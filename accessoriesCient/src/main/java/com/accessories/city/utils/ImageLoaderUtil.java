package com.accessories.city.utils;

import android.graphics.Bitmap.Config;
import com.accessories.city.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class ImageLoaderUtil {

    /*
     * public static DisplayImageOptions mUserIconLoaderOptions = new
     * DisplayImageOptions
     * .Builder().showImageForEmptyUri(R.drawable.head_default)
     * .bitmapConfig(Config
     * .RGB_565).showImageOnFail(R.drawable.head_default).cacheInMemory
     * (true).cacheOnDisk(true)
     * .showImageOnLoading(R.drawable.head_default).displayer(new
     * SimpleBitmapDisplayer()).build();
     *
     *
     * public static DisplayImageOptions mShopIconLoaderOptions = new
     * DisplayImageOptions
     * .Builder().showImageForEmptyUri(R.drawable.friends_sends_pictures_no)
     * .showImageOnLoading
     * (R.drawable.friends_sends_pictures_no).bitmapConfig(Config
     * .RGB_565).showImageOnFail(R.drawable.friends_sends_pictures_no)
     * .cacheInMemory(true).cacheOnDisk(true).displayer(new
     * SimpleBitmapDisplayer()).build();
     */
//    public static DisplayImageOptions mUserIconLoaderOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_user_portrait).bitmapConfig(Config.RGB_565)
//            .showImageOnFail(R.drawable.default_user_portrait).cacheInMemory(true).cacheOnDisk(true).showImageOnLoading(R.drawable.default_user_portrait).displayer(new SimpleBitmapDisplayer()).build();

    public static DisplayImageOptions mHallIconLoaderOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.rr).showImageOnLoading(R.drawable.rr).bitmapConfig(Config.RGB_565)
            .showImageOnFail(R.drawable.rr).cacheInMemory(true).cacheOnDisk(true).displayer(new SimpleBitmapDisplayer()).build();

	/*
     * public static DisplayImageOptions mCollectCaterOptions=new
	 * DisplayImageOptions
	 * .Builder().showImageForEmptyUri(R.drawable.collect_cater_img)
	 * .showImageOnLoading
	 * (R.drawable.collect_cater_img).bitmapConfig(Config.RGB_565
	 * ).showImageOnFail(R.drawable.collect_cater_img)
	 * .cacheInMemory(true).cacheOnDisk(true).displayer(new
	 * SimpleBitmapDisplayer()).build();
	 * 
	 * public static DisplayImageOptions mHomeproductCateLoaderOptions = new
	 * DisplayImageOptions
	 * .Builder().showImageForEmptyUri(R.drawable.ic_hall_no_url)
	 * .showImageOnLoading
	 * (R.drawable.ic_hall_no_url).bitmapConfig(Config.RGB_565
	 * ).showImageOnFail(R.drawable.ic_hall_no_url)
	 * .cacheInMemory(true).cacheOnDisk(true).displayer(new
	 * RoundedBitmapDisplayer(15)).build();
	 */
}
