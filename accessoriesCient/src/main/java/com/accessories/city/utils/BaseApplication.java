package com.accessories.city.utils;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.accessories.city.BuildConfig;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.service.LocationService;
import com.accessories.city.service.LocationUitl;
import com.baidu.location.BDLocation;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.jpush.im.android.api.JMessageClient;
import io.jchat.android.application.JChatDemoApplication;
import io.jchat.android.chatting.utils.SharePreferenceManager;
import io.jchat.android.receiver.NotificationClickEventReceiver;


/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/3/15
 */
public class BaseApplication extends JChatDemoApplication {

    private static final String JCHAT_CONFIGS = "JChat_configs";
    private static BaseApplication  instance;

    public String location[] = new String[2];// 城市、城市编码
    public LocationService locationService;
    public BDLocation mapLocation;
    public Vibrator mVibrator;

    private static UserInfo userInfo;

//    public String userId = "0" ;//用户Id,默认为0；
    public String appVersion = "";//版本
    private static String accessToken = "";//校验
//    public String accessToken = "00000000";//校验
    public String address = "";//
    public LocationUitl locationUitl = new LocationUitl();

    public static String diviceId = "";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        WriteLog.getInstance().init(); // 初始化日志
//        SDKInitializer.initialize(getApplicationContext());

//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush

        Log.i("JpushDemoApplication", "init");
        //初始化JMessage-sdk
        JMessageClient.init(this);
        SharePreferenceManager.init(getApplicationContext(), JCHAT_CONFIGS);
        //设置Notification的模式
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());

        initImageLoader();
        appVersion = AppManager.getVersion(this);
        address = AppManager.getLocalMacAddressFromWifiInfo(this);
        URLConstants.SCREENW = ScreenUtils.getScreenWidth(this);
        URLConstants.SCREENH = ScreenUtils.getScreenHeight(this);
        locationUitl.startLocation();
        location[0] = "南昌市";
        location[1] = "115";
        getLocation();
        diviceId = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE )).getDeviceId();
    }


    public static BaseApplication getInstance(){
        return instance;
    }


    public static UserInfo getUserInfo(){
        if(userInfo == null){
            userInfo = ContextUtils.getObjFromSp(BaseApplication.getInstance(),"userInfo");
        }
        return  userInfo;
    }

    public static void saveUserInfo(UserInfo userInfo){
        BaseApplication.userInfo = userInfo;
        ContextUtils.saveObj2SP(BaseApplication.getInstance(),userInfo,"userInfo");
    }

    public static String getMt_token() {
        if(TextUtils.isEmpty(accessToken)){
            accessToken =  BaseApplication.getInstance().getApplicationContext()
                    .getSharedPreferences("accessToken", Context.MODE_PRIVATE).getString("accessToken","");
        }
        if(TextUtils.isEmpty(accessToken)){
            accessToken = "00000000";
        }
        return accessToken;
    }

    public static void setMt_token(String accessToken) {
        BaseApplication.getInstance().getApplicationContext()
                .getSharedPreferences("accessToken", Context.MODE_PRIVATE).edit().putString("accessToken",accessToken).commit();
        BaseApplication.accessToken = accessToken;
    }

    public static boolean isLogin(){
        return getUserInfo() != null && !TextUtils.isEmpty(userInfo.getId());
    }

    public  void saveLocation(String cityName,String cityId){
        location[0] = cityName;
        location[1] = cityId;
        ContextUtils.saveObj2SP(BaseApplication.getInstance(),location,"location");
    }


    public  void getLocation(){
        String locat[] = ContextUtils.getObjFromSp(BaseApplication.getInstance(),"location");

        if(locat != null && locat.length >0){
            location = locat;
        }
    }

    private void initImageLoader() {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default_car configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024)).memoryCacheSize(5 * 1024 * 1024).diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                .tasksProcessingOrder(QueueProcessingType.LIFO) // Not
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);


    }



}
