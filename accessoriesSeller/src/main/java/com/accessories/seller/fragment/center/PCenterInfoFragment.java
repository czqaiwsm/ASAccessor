package com.accessories.seller.fragment.center;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accessories.seller.R;
import com.accessories.seller.activity.TeacherMainActivity;
import com.accessories.seller.activity.center.PCenterModifyInfoActivity;
import com.accessories.seller.activity.center.QueryCarActivity;
import com.accessories.seller.activity.center.ServiceProtocolActivity;
import com.accessories.seller.activity.center.WidthdrawInfoActivity;
import com.accessories.seller.activity.center.WidthdrawRecordActivity;
import com.accessories.seller.activity.home.PhoneRecordActivity;
import com.accessories.seller.activity.login.SellerLoginActivity;
import com.accessories.seller.bean.SellerInfo;
import com.accessories.seller.bean.SellerUserInfo;
import com.accessories.seller.bean.UploadBean;
import com.accessories.seller.bean.UserInfo;
import com.accessories.seller.fragment.BaseFragment;
import com.accessories.seller.help.RequsetListener;
import com.accessories.seller.parse.LoginInfoParse;
import com.accessories.seller.utils.AlertDialogUtils;
import com.accessories.seller.utils.AppLog;
import com.accessories.seller.utils.AppManager;
import com.accessories.seller.utils.BaseApplication;
import com.accessories.seller.utils.DataCleanManager;
import com.accessories.seller.utils.ImageFactory;
import com.accessories.seller.utils.NetUtils;
import com.accessories.seller.utils.SDCardUtils;
import com.accessories.seller.utils.SmartToast;
import com.accessories.seller.utils.URLConstants;
import com.accessories.seller.utils.Utils;
import com.accessories.seller.utils.WaitLayer;
import com.accessories.seller.view.RoundImageView;
import com.accessories.seller.view.UpdateAvatarPopupWindow;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.eventbus.EventBus;
import cn.jpush.im.api.BasicCallback;
import io.jchat.android.chatting.utils.FileHelper;
import io.jchat.android.chatting.utils.HandleResponseCode;
import io.jchat.android.chatting.utils.SharePreferenceManager;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 我
 *
 * @author ccs7727@163.com
 * @time 2015年9月28日上午11:44:26
 *
 */
public class PCenterInfoFragment extends BaseFragment implements OnClickListener,RequsetListener {

    private RelativeLayout wallet_layout;
    private RelativeLayout order_layout;
    private RelativeLayout custom_layout;
    private RelativeLayout setting_layout;
    private RelativeLayout wdrawRl;
    private RelativeLayout queryCarRl;
    private RelativeLayout widthdrawRecordRl;
    private RelativeLayout clearRl;

    private SellerUserInfo mUserInfo;
    private TextView account_customname;
    private TextView account_ordername;
    private TextView editNameTv;
    private TextView name;
    private TextView clearTv;
    private RoundImageView headRImg;
    private boolean isPrepare = false;
    private boolean isVisible = false;


    UpdateAvatarPopupWindow m_obj_menuWindow ;
    private Bitmap m_obj_IconBp = null;
    private static final int REQUEST_PHOTO = 4;// 相册选择头像
    private static final int REQUEST_CAMEIA = 5;// 相机选择头像
    private static final int RESULT_REQUEST = 6;// 裁剪的结果
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "avatar.jpg";


    // 需要清除缓存的文件
    private File file1;// 内部缓存
    private File file2;// 外部缓存

    // 选择相册还是拍照
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            m_obj_menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_camera:
                    goCamera();
                    break;
                case R.id.btn_photo:
                    Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                    getImage.setType("image/*");
                    startActivityForResult(getImage, REQUEST_PHOTO);
                    break;
            }

        }

    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo = BaseApplication.getSellerUserInfo();

        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pcenter_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        file1 = getActivity().getCacheDir();// 内部缓存
        file2 = getActivity().getExternalCacheDir();// 外部缓存
        initTitleView();
        initView(view);
        isPrepare = true;
        onLazyLoad();
    }

    private void initTitleView() {
        setTitleText(R.string.me_tab);
        setHeaderRightText(R.string.login_out, new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogUtils.displayMyAlertChoice(mActivity, "提示", "确定退出应用!", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(BaseApplication.isLogin()){
                            logoutIM();
                            BaseApplication.saveSellerUserInfo(null);
                        }
                        TeacherMainActivity.exit = true;
                        AppManager.getAppManager().finishAllActivity();
                    }
                },null);
            }
        });
//        setRightHeadIcon(R.drawable.pc_search_right,new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toClassActivity(PCenterInfoFragment.this,MsgChooseActivity.class.getName());
//            }
//        });
    }

    //退出登录
    public void logoutIM() {
        // TODO Auto-generated method stub
//        final Intent intent = new Intent();
        cn.jpush.im.android.api.model.UserInfo info = JMessageClient.getMyInfo();
        if (null != info) {
//            intent.putExtra("userName", info.getUserName());
            File file = info.getAvatarFile();
            if (file != null && file.isFile()) {
//                intent.putExtra("avatarFilePath", file.getAbsolutePath());
            } else {
                String path = FileHelper.getUserAvatarPath(info.getUserName());
                file = new File(path);
                if (file.exists()) {
//                    intent.putExtra("avatarFilePath", file.getAbsolutePath());
                }
            }
            SharePreferenceManager.setCachedUsername(info.getUserName());
            SharePreferenceManager.setCachedAvatarPath(file.getAbsolutePath());
            JMessageClient.logout();
//            intent.setClass(mContext, ReloginActivity.class);
//            startActivity(intent);
        } else {
            Log.i("TAG", "user info is null!");
        }
    }

    private void onLazyLoad(){
        if(isPrepare && isVisible){
            requestData(1);
            setData(BaseApplication.getSellerUserInfo());
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
        onLazyLoad();
    }

    private void initView(View v) {
        wallet_layout = (RelativeLayout) v.findViewById(R.id.wallet_layout);
        order_layout = (RelativeLayout) v.findViewById(R.id.order_layout);
        custom_layout = (RelativeLayout) v.findViewById(R.id.custom_layout);
        setting_layout = (RelativeLayout) v.findViewById(R.id.set_layout);
        queryCarRl = (RelativeLayout) v.findViewById(R.id.queryCarRl);
        wdrawRl = (RelativeLayout) v.findViewById(R.id.wdrawRl);
        widthdrawRecordRl = (RelativeLayout) v.findViewById(R.id.widthdrawRecordRl);
        clearRl = (RelativeLayout) v.findViewById(R.id.clearRl);
        account_customname = (TextView)v.findViewById(R.id.account_customname);
        account_ordername = (TextView)v.findViewById(R.id.account_ordername);
        editNameTv = (TextView)v.findViewById(R.id.editNameTv);
        name = (TextView)v.findViewById(R.id.name);
        clearTv = (TextView)v.findViewById(R.id.clearTv);
        headRImg = (RoundImageView) v.findViewById(R.id.headRImg);

        wallet_layout.setOnClickListener(this);
        order_layout.setOnClickListener(this);
        custom_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);
        wdrawRl.setOnClickListener(this);
        queryCarRl.setOnClickListener(this);
        editNameTv.setOnClickListener(this);
        widthdrawRecordRl.setOnClickListener(this);
        clearRl.setOnClickListener(this);
//        name.setOnClickListener(this);
        headRImg.setOnClickListener(this);

        wdrawRl.setClickable(true);
        setData(mUserInfo);

    }

    @Override
    public void onResume() {
        super.onResume();
        setData(BaseApplication.getSellerUserInfo());
        setCache();
    }

    private void setData(SellerUserInfo userInfo) {
        account_customname.setText("0791-86275003");
        if(userInfo != null){

//            String money = "<span>"+getString(R.string.integeral, userInfo.getIntegral())+"<font color='#0099FF'>(￥"+userInfo.getMoney()+")</font></span>";
//            account_ordername.setText(Html.fromHtml(money));
            ImageLoader.getInstance().displayImage(userInfo.getShopPic(), headRImg);
            name.setText(TextUtils.isEmpty(userInfo.getShopName())?"":userInfo.getShopName());
        }


    }

    public void onEvent(UserInfo userInfo){
        requestTask(1);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.editNameTv://修改昵称
                toClassActivity(PCenterInfoFragment.this, PCenterModifyInfoActivity.class.getName());
                break;
            case R.id.headRImg:// 头像
                photoSet();
                break;
            case R.id.wallet_layout:// 关于我们
                intent = new Intent(mActivity, ServiceProtocolActivity.class);
                intent.setFlags(12);
                intent.putExtra("url","www.baidu.com");
                mActivity.startActivity(intent);
//                toClassActivity(PCenterInfoFragment.this, WalletActivity.class.getName());
                break;
            case R.id.order_layout:// 积分
//                toClassActivity(PCenterInfoFragment.this, OrderActivity.class.getName());
                break;
            case R.id.custom_layout:// 联系我们
                //用intent启动拨打电话
                if(TextUtils.isEmpty(account_customname.getText().toString())){
                    toasetUtil.showInfo("暂无客服电话!");
                    return;
                }
                callPhone();
                break;
            case R.id.wdrawRl:// 提现

                if(Integer.valueOf(BaseApplication.getUserInfo().getIntegral())==0){
                    SmartToast.showText("您的积分不足,不能提现");
                    return;
                }
                wdrawRl.setClickable(false);
                requestTask(3);
//                intent = new Intent(mActivity,WidthdrawInfoActivity.class);
//                intent.setFlags(1);
//                startActivity(intent);
                break;
            case R.id.widthdrawRecordRl:// 提现记录
                intent = new Intent(mActivity,WidthdrawRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.name://
                intent = new Intent(mActivity,WidthdrawInfoActivity.class);
                intent.setFlags(2);
                startActivity(intent);
                break;
            case R.id.queryCarRl:// 车驾码查询
                toClassActivity(PCenterInfoFragment.this, QueryCarActivity.class.getName());
                break;
            case R.id.set_layout:// 设置
                toClassActivity(PCenterInfoFragment.this, PhoneRecordActivity.class.getName());
                break;
            case R.id.clearRl:// 清理缓存
                clearCache();
                break;

        }

    }

    @Override
    protected void requestData(int requestCode) {

        HttpURL url = new HttpURL();
        Map postParams = new HashMap<String,String>();
        RequestParam param = new RequestParam();
        switch (requestCode){
            case 1:
                url.setmBaseUrl(URLConstants.SHOPDETAIL);
                postParams.put("shopId",BaseApplication.getSellerUserInfo().getShopId());
                break;
            case 2:
                url.setmBaseUrl(URLConstants.UPDATE_SHOP);
                postParams.put("id", mUserInfo.getShopId());
                postParams.put("shopName",mUserInfo.getShopName());
                postParams.put("shopPic",((UploadBean)headRImg.getTag()).getFilePath().toString());
                break;
            case 3:
                url.setmBaseUrl(URLConstants.CONSTANT);
                postParams.put("type", "integral_type");
                postParams.put("key","cash");
                break;

        }
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestCode), createMyReqErrorListener(), param);
    }
    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        switch (requestType){
            case 1:
                JsonParserBase<SellerInfo> jsonParserBase = ParserUtil.fromJsonBase(obj.toString(), new TypeToken<JsonParserBase<SellerInfo>>() {
                }.getType());
                SellerInfo balanceInfo = jsonParserBase.getObj();
                if(balanceInfo != null){
                    BaseApplication.getSellerUserInfo().setVip(balanceInfo.getVip());
                    BaseApplication.saveSellerUserInfo(BaseApplication.getSellerUserInfo());
                }
//                JsonParserBase<UserInfo> jsonParserBase = (JsonParserBase<UserInfo>)obj;
//                if ((jsonParserBase != null)){
//                    mUserInfo = jsonParserBase.getObj();
//                    BaseApplication.saveUserInfo(jsonParserBase.getObj());
//                    BaseApplication.setMt_token(jsonParserBase.getObj().getId());
//                    JPushInterface.setAlias(BaseApplication.getInstance(), "t_" + BaseApplication.getUserInfo().getId(), null);
//                    setData(mUserInfo);
//                }
                break;
            case 2:
                BaseApplication.getSellerUserInfo().setShopPic(((UploadBean)headRImg.getTag()).getAbsFilePath());
                BaseApplication.saveSellerUserInfo(BaseApplication.getSellerUserInfo());
                SmartToast.showText("头像修改过成功!");
                break;
//            case 3:
//                wdrawRl.setClickable(true);
//                JsonParserBase<Value> jsonBase =  ParserUtil.fromJsonBase(obj.toString(), new TypeToken<JsonParserBase<Value>>() {
//                }.getType());
//                if(jsonBase.getObj().getValue()< Integer.valueOf(mUserInfo.getIntegral())){
//                    URLConstants.WIDTHDRAW_INTEGRAL = jsonBase.getObj().getValue();
//                    Intent intent = new Intent(mActivity,WidthdrawInfoActivity.class);
//                    intent.setFlags(1);
//                    startActivity(intent);
//                }else {
//                    SmartToast.showText("您的积分不足,不能提现");
//                }
//                break;

        }

    }

    @Override
    protected void failRespone() {
        super.failRespone();
        wdrawRl.setClickable(true);
    }

    @Override
    protected void errorRespone() {
        super.errorRespone();
        wdrawRl.setClickable(true);
    }

    String cache_size = "";

    /**
     * 获取缓存
     */
    private void setCache() {
        try {
            long a = DataCleanManager.getFolderSize(file1);
            long b = DataCleanManager.getFolderSize(file2);
//			long d = DataCleanManager.getFolderSize(file3);
            cache_size = DataCleanManager.getFormatSize(a + b );
            clearTv.setText(cache_size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除缓存
     */
    private void clearCache(){
        file1 = getActivity().getCacheDir();// 内部缓存
        file2 = getActivity().getExternalCacheDir();// 外部缓存
//			file3 = new File("/data/data/" + getActivity().getPackageName() + "/databases");// 数据库缓存
        List<String> fileList = new ArrayList<String>();
        if (file1 != null) {
            fileList.add(getActivity().getCacheDir().getAbsolutePath());
        }
        if (file2 != null) {
            fileList.add(getActivity().getExternalCacheDir().getAbsolutePath());
        }
//			if (file3 != null) {
//				fileList.add(new File("/data/data/" + getActivity().getPackageName() + "/databases").getAbsolutePath());
//			}
        DataCleanManager.cleanApplicationData(fileList);
//			Constant.CLEAR_SEARCH = true;
        SmartToast.showText(mActivity, "成功清理缓存" + cache_size);
        setCache();
    }


    private void call(){
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+account_customname.getText().toString()));
        startActivity(intent);
    }

    private void callPhone() {
        //检查权限
        if (ContextCompat.checkSelfPermission(BaseApplication.getInstance(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CALL_PHONE)) {

                new AlertDialog.Builder(mActivity)
                        .setMessage("需要开启权限才能拨打电话")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + BaseApplication.getInstance().getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            } else {

                //申请权限
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                        100);
            }
//
        } else {
            //已经拥有权限进行拨打
            call();
        }
    }


    private void goCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
            startActivityForResult(takeIntent, REQUEST_CAMEIA);
        } else {
            Toast.makeText(getActivity(), "无法拍照", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras == null) {
            return;
        }
        Bitmap bitmap = extras.getParcelable("data");
        Drawable drawable = new BitmapDrawable(bitmap);
//         mHeadImg.setImageDrawable(drawable);
        /********** 上传图片 ***************/
        m_obj_IconBp = bitmap;// 用于上传服务器
        setLoadingDilog(WaitLayer.DialogType.MODALESS);
        showLoadingDilog("正在上传...");
        if (NetUtils.isConnected(getActivity())) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    uploadFileM();
                }
            }, "upPic1").start();
        } else {
            toasetUtil.showInfo("网络连接出错,无法上传头像");
        }

    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= 19;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private void photoSet() {
        //检查权限
        if (ContextCompat.checkSelfPermission(BaseApplication.getInstance(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                new AlertDialog.Builder(mActivity)
                        .setMessage("需要开启权限才能访问SD卡")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + BaseApplication.getInstance().getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            } else {

                //申请权限
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        200);
            }
//
        } else {
            //已经拥有权限进行拨打
            photoPpw();
        }
    }

    private void photoPpw() {
        m_obj_menuWindow = new UpdateAvatarPopupWindow(getActivity(),wdrawRl, itemsOnClick);
        m_obj_menuWindow.showAtLocation(wdrawRl, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMEIA:// 来自相机，去裁剪
                    if (data == null) {
                        if (SDCardUtils.checkSDCardStatus()) {
                            File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                            startPhotoZoom(Uri.fromFile(tempFile));
                        } else {
                            Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                        }
                    } else if (data.getExtras() != null) {
                        if (SDCardUtils.checkSDCardStatus()) {
                            File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                            startPhotoZoom(Uri.fromFile(tempFile));
                        } else {
                            Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case REQUEST_PHOTO:// 来自相册，去裁剪
                    if (data != null) {
                        Uri mImageUri = data.getData();
                        String path = getPath(getActivity(), mImageUri);
                        mImageUri = Uri.fromFile(new File(path));
                        try {
                            startPhotoZoom(mImageUri);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case RESULT_REQUEST:// 保存修改的头像并上传服务器
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        setData(mUserInfo);
        super.onActivityResult(requestCode, resultCode, data);
    }


    // 解析服务器响应的数据
    private void parseDataFromServer(String data) {
        try {
            System.out.println("data:"+data);

            if (data != null) {
                UploadBean result = ParserUtil.fromJson(data, new TypeToken<UploadBean>() {
                }.getType());
                if (result != null && URLConstants.SUCCESS_CODE.equals(result.getResult())) {
                    headRImg.setImageBitmap(m_obj_IconBp);
                    headRImg.setTag(result);
                    String temp =  Utils.saveBitmap2file(m_obj_IconBp,"im_chat_head.jpg");
                    if(TextUtils.isEmpty(temp)) {
                        Log.e("error:","头像本地化失败");
                        dismissLoadingDilog();
                        return;
                    }
                    JMessageClient.updateUserAvatar(new File(temp), new BasicCallback() {
                        @Override
                        public void gotResult(final int status, final String desc) {
                            if (status == 0) {
                                requestData(2);
                            }else {
                                HandleResponseCode.onHandle(mActivity, status, false);
                                dismissLoadingDilog();
                            }
                        }
                    });
//                    requestTask(2);
                } else {
                    toasetUtil.showInfo("上传失败,请重新上传!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dismissLoadingDilog();
        }
    }


    private void uploadFileM() {

        AppLog.Loge("开始上传头像------------");
        String fore_name = UUID.randomUUID().toString();
        String fileName = fore_name + ".jpg"; // 报文中的文件名参数
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        m_obj_IconBp = Utils.compressImage(m_obj_IconBp);// 压缩到100kb
        byte[] picByte = BitMap2Byte(m_obj_IconBp);
        try {
            URL url = new URL(URLConstants.FILE_UPLOAD);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /*
             * Output to the connection. Default is false, set to true because
			 * post method must write something to the connection
			 */
            con.setDoOutput(true);
			/* Read from the connection. Default is true. */
            con.setDoInput(true);
			/* Post cannot use caches */
            con.setUseCaches(false);
			/* Set the post method. Default is GET */
            con.setRequestMethod("POST");
			/* 设置请求属性 */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			/* 设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接 */
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
			/* 设置DataOutputStream，getOutputStream中默认调用connect() */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            //avatar 需要与后台约定的字段.
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"avatar\";filename=\"" + fileName + "\"" + end);
            ds.writeBytes(end);
            AppLog.Logi(PCenterInfoFragmentUser.class + "", "图片字节:" + picByte.toString());
            ds.write(picByte, 0, picByte.length);
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

            ds.close();
			/* 从返回的输入流读取响应信息 */
            InputStream is = con.getInputStream(); // input from the connection
            // 正式建立HTTP连接
            int ch;
            final StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
			/* 显示网页响应内容 */
            // Toast.makeText(getActivity(), b.toString().trim(),
            // Toast.LENGTH_SHORT).show();// Post成功
            AppLog.Logi(PCenterInfoFragmentUser.class + "", "响应内容:" + b.toString().trim());
            // 解析响应的数据
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parseDataFromServer(b.toString().trim());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            dismissLoadingDilog();
			/* 显示异常信息 */
            Message msg = handler.obtainMessage();
            msg.what = SHOW_ERROR;
            msg.obj = getResources().getString(R.string.upload_fail);
            handler.removeMessages(msg.what);
            handler.sendMessage(msg);
        }
    }

    // 将BitMap转换成字节流
    private byte[] BitMap2Byte(Bitmap bitmap) {
        if (null == bitmap) {
            return null;
        }
        bitmap = ImageFactory.ratio(bitmap,200,200);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        AppLog.Logi(PCenterInfoFragmentUser.class + "", "byte = " + byteArray);
        AppLog.Logi(PCenterInfoFragmentUser.class + "", "byteString = " + byteArray.toString());
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                call();
            } else
            {
                // Permission Denied
                SmartToast.showText("您拒绝了拨打电话权限!");
            }
            return;
        }else if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                photoPpw();
            } else {
                // Permission Denied
                SmartToast.showText("您拒绝了SD卡访问权限!");
            }
            return;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
