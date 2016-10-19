package com.accessories.city.fragment.center;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.*;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.accessories.city.R;
import com.accessories.city.activity.ChooseCityActivity;
import com.accessories.city.activity.center.EditActivity;
import com.accessories.city.activity.center.PCenterModifyInfoActivity;
import com.accessories.city.activity.teacher.ChooseJoinorActivity;
import com.accessories.city.bean.DataMapConstants;
import com.accessories.city.bean.UploadBean;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.*;
import com.accessories.city.view.RoundImageView;
import com.accessories.city.view.UpdateAvatarPopupWindow;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

/**
 * 个人资料-用户
 *
 * @author ccs7727@163.com
 * @time 2015年9月28日上午11:44:26
 *
 */
public class PCenterInfoFragmentUser extends BaseFragment implements OnClickListener,RequsetListener {

    private RelativeLayout photo_layout;
    private RelativeLayout name_layout;
    private RelativeLayout sex_layout;
    private RelativeLayout jonior_layout;
    private RelativeLayout city_layout;
    private RelativeLayout signature_layout ;
    private RelativeLayout indure_layout    ;
    private RelativeLayout reputation_layout;
    private RelativeLayout advice_layout;

    private TextView name;
    private TextView sexTxt;
    private TextView jonior;
    private TextView city;
    private TextView signature  ;
    private TextView indure     ;
    private TextView reputation;

    private String requstValue = "";//请求的id
    private int requestCode;
    private UserInfo mUserInfo;

    private int update_data = -1;// 1:昵称 2:qq 3:邮箱 4:性别
    private String[] items2 = new String[] { "男", "女" };
    private int sex = -1;// 选择性别
    ScrollView scrollview;
    private final int MODIFY_NAME =  10;//个人姓名
    private final int MODIFY_GENDER =20;//个人性别
    private final int MODIFY_CITY =  30;//城市
    private final int MODIFY_SIGN =  40;//签名
    private final int MODIFY_BRIEF = 50;//简历
    private final int MODIFY_REPUT = 60;//荣誉


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pcenter_info_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView(view);
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText(R.string.center_user_manage);
    }

    private void initView(View v) {
        scrollview = (ScrollView) v.findViewById(R.id.scrollview);
        mHeadImg = (RoundImageView) v.findViewById(R.id.account_head_img);
        photo_layout = (RelativeLayout) v.findViewById(R.id.photo_avatar_layout);
        name_layout = (RelativeLayout) v.findViewById(R.id.name_layout);
        sex_layout = (RelativeLayout) v.findViewById(R.id.sex_layout);
        jonior_layout = (RelativeLayout) v.findViewById(R.id.jonior_layout);
        city_layout = (RelativeLayout) v.findViewById(R.id.city_layout);
        signature_layout  = (RelativeLayout) v.findViewById(R.id.signature_layout );
        indure_layout     = (RelativeLayout) v.findViewById(R.id.indure_layout    );
        reputation_layout = (RelativeLayout) v.findViewById(R.id.reputation_layout);
        advice_layout = (RelativeLayout) v.findViewById(R.id.advice_layout);
        name = (TextView)v.findViewById(R.id.nick_name);
        sexTxt = (TextView)v.findViewById(R.id.account_sexname);
        jonior = (TextView)v.findViewById(R.id.account_joniorname);
        city = (TextView)v.findViewById(R.id.account_cityname);
        signature  = (TextView)v.findViewById(R.id.signature );
        indure     = (TextView)v.findViewById(R.id.indure    );
        reputation = (TextView)v.findViewById(R.id.reputation);

        photo_layout.setOnClickListener(this);
        name_layout.setOnClickListener(this);
        sex_layout.setOnClickListener(this);
        jonior_layout.setOnClickListener(this);
        signature_layout.setOnClickListener(this);
        indure_layout.setOnClickListener(this);
        reputation_layout.setOnClickListener(this);
        city_layout.setOnClickListener(this);
        city.setText(SharePreferenceUtils.getInstance(BaseApplication.getInstance()).getCityName());

        setData(BaseApplication.getUserInfo());
    }

    private void setData(UserInfo userInfo) {
        if(userInfo != null){
//            ImageLoader.getInstance().displayImage(userInfo.getHeadImg(), mHeadImg, ImageLoaderUtil.mHallIconLoaderOptions);
//            name.setText(userInfo.getNickName());
//            sexTxt.setText(DataMapConstants.getGender().get(userInfo.getGender()));
//            signature.setText(userInfo.getSignature());
//            indure.setText(userInfo.getIntroduction());
//            reputation.setText(userInfo.getExperience());
//            city.setText(userInfo.getCity());
        }

        advice_layout.setVisibility(View.GONE);

        if(TextUtils.isEmpty(city.getText().toString()) || TextUtils.isEmpty(indure.getText().toString())){
            advice_layout.setVisibility(View.VISIBLE);
        }

        ArrayMap map = null;

    }

    private Intent intent ;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.photo_avatar_layout:// 头像
                m_obj_menuWindow = new UpdateAvatarPopupWindow(getActivity(), v, itemsOnClick);
            m_obj_menuWindow.showAtLocation(scrollview, Gravity.BOTTOM, 0, 0);
            break;
            case R.id.name_layout:// 姓名
                intent = new Intent(mActivity, PCenterModifyInfoActivity.class);
                intent.putExtra("flag",1);
                startActivityForResult(intent,MODIFY_NAME);
                break;
            case R.id.sex_layout:// 性别
            intent = new Intent(mActivity, PCenterModifyInfoActivity.class);
            intent.putExtra("flag",8);
            startActivityForResult(intent,MODIFY_GENDER);
            break;
            case R.id.jonior_layout:// 年级
            intent = new Intent(mActivity, ChooseJoinorActivity.class);
            startActivityForResult(intent, URLConstants.CHOOSE_JOINOR_REQUEST_CODE);
            break;
            case R.id.city_layout:// 城市
            intent = new Intent(mActivity, ChooseCityActivity.class);
            startActivityForResult(intent,MODIFY_CITY);
            break;
            case R.id.signature_layout:// 签名
            intent = new Intent(mActivity, EditActivity.class);
                intent.setFlags(MODIFY_SIGN);
            startActivityForResult(intent,MODIFY_SIGN);
            break;
            case R.id.indure_layout:// 简介
            intent = new Intent(mActivity, EditActivity.class);
                intent.setFlags(MODIFY_BRIEF);
            startActivityForResult(intent,MODIFY_BRIEF);
            break;
            case R.id.reputation_layout:// 荣耀
                intent = new Intent(mActivity, EditActivity.class);
                intent.setFlags(MODIFY_REPUT);
            startActivityForResult(intent,MODIFY_REPUT);
            break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
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
//                case Constant.MODIFY_INFO:// 修改个人信息
//                    requestData();
//                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {



//            姓名	1
//            性别	2
//            所在地	3
//            签名	4
//            简介	5
//            荣誉经历	6

            switch (requestCode) {
                case MODIFY_NAME://姓名
                    requstValue = data.getStringExtra("name");
                    requestTask(1);
                    break;
                case MODIFY_GENDER://性别
                    requstValue = data.getStringExtra("gender");
                    requestTask(2);
                    break;
                case MODIFY_CITY://城市
                    requstValue = data.getStringExtra("city");
                    city.setText(SharePreferenceUtils.getInstance(BaseApplication.getInstance()).getCityName());
                    requestTask(3);
                    break;
                case URLConstants.CHOOSE_JOINOR_REQUEST_CODE:
                requstValue = data.getStringExtra(URLConstants.CHOOSE);
                requestTask();
                break;
                case MODIFY_SIGN://签名
                    requstValue = data.getStringExtra("value");
                    requestTask(4);
                    break;
                case MODIFY_BRIEF://简介
                    requstValue = data.getStringExtra("value");
                    requestTask(5);
                    break;
                case MODIFY_REPUT://进来
                    requstValue = data.getStringExtra("value");
                    requestTask(6);
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 请求 用户信息
     */
    @Override
    public void requestData(int requestType) {
        // TODO Auto-generated method stub
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("UserInfoEdit");
        postParams.put("editType", requestType);
        postParams.put("editValue", requstValue);
        RequestParam param = new RequestParam();
//        param.setmParserClassName(LoginInfoParse.class.getName());
//        param.setmParserClassName(new LoginInfoParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        switch (requestCode){
            case URLConstants.CHOOSE_JOINOR_REQUEST_CODE://年级选择
//                BaseApplication.getUserInfo().setGrade(requstValue);
                jonior.setText(DataMapConstants.getJoniorMap().get(requstValue));
                break;
//            case MODIFY_GENDER://性别
//                BaseApplication.getUserInfo().setGender(requstValue);
//                sexTxt.setText(DataMapConstants.getGender().get(requstValue));
//                break;
//            case MODIFY_NAME://姓名
//                BaseApplication.getUserInfo().setNickName(requstValue);
//                name.setText(requstValue);
//                break;
//            case MODIFY_SIGN://签名
//                BaseApplication.getUserInfo().setSignature(requstValue);
//                signature.setText(requstValue);
//                break;
//            case MODIFY_BRIEF://简介
//                BaseApplication.getUserInfo().setIntroduction(requstValue);
//                indure.setText(requstValue);
//                break;
//            case MODIFY_REPUT://进来
//                BaseApplication.getUserInfo().setExperience(requstValue);
//                reputation.setText(requstValue);
//                break;
        }

        advice_layout.setVisibility(View.GONE);
        BaseApplication.saveUserInfo(BaseApplication.getUserInfo());
        if(TextUtils.isEmpty(city.getText().toString()) || TextUtils.isEmpty(indure.getText().toString())){
            advice_layout.setVisibility(View.VISIBLE);
        }
    }

    /******************************************** 修改头像start *****************************************************/
    private RoundImageView mHeadImg;
    // popupwidos
    private UpdateAvatarPopupWindow m_obj_menuWindow = null;
    // 头像BitMap
    private Bitmap m_obj_IconBp = null;
    private String userId = "";
    private String postUrl = "";
    private String m_str_uploadMsg = "";
    // 上传图片的状态
    private static final int UPLOAD_START = 1;// 开始上传
    private static final int UPLOAD_OK = 2;// 结束上传(成功)
    private static final int UPLOAD_ERROR = 3;// 结束上传(失败)
    private static final int REQUEST_PHOTO = 4;// 相册选择头像
    private static final int REQUEST_CAMEIA = 5;// 相机选择头像
    private static final int RESULT_REQUEST = 6;// 裁剪的结果
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "avatar.jpg";

    // 选择相册还是拍照
    private OnClickListener itemsOnClick = new OnClickListener() {

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
            postUrl = URLConstants.TEACHER_UPLOAD;
            String param = new String();
            param = "cmd=UploadHead"  + "&appVersion=" + BaseApplication.getInstance().appVersion+"&clientType=3" +
                    "&accessToken=" + BaseApplication.getMt_token()+"&deviceId="+BaseApplication.diviceId+"&spaceCode=1005";

            postUrl = postUrl+"?"+param;
            URL url = new URL(postUrl);
            AppLog.Logi(PCenterInfoFragmentUser.class+"", "url = " + postUrl);
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
            AppLog.Logi(PCenterInfoFragmentUser.class+"", "图片字节:" + picByte.toString());
            ds.write(picByte, 0, picByte.length);
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

            ds.writeBytes(param);
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
			/* 显示异常信息 */
            Message msg = handler.obtainMessage();
            msg.what = SHOW_ERROR;
            msg.obj = getResources().getString(R.string.upload_fail);
            handler.removeMessages(msg.what);
            handler.sendMessage(msg);
        }
    }

    // 解析服务器响应的数据
    private void parseDataFromServer(String data) {
        try {

            dismissLoadingDilog();
            if (data != null){
                JsonParserBase<UploadBean> result = ParserUtil.fromJsonBase(data, new TypeToken<JsonParserBase<UploadBean>>() {
                }.getType());
                if(result != null && URLConstants.SUCCESS_CODE.equals(result.getResult())){
                    UploadBean jsonStr = result.getObj();

                    if(jsonStr != null){

                        if(!TextUtils.isEmpty(jsonStr.getImgPath())){
//                            BaseApplication.getUserInfo().setHeadImg(jsonStr.getImgPath());
                            myHandler.sendEmptyMessage(UPLOAD_OK);
                        }
                    }
                }else{
                    toasetUtil.showInfo( result.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    // 将BitMap转换成字节流
    private byte[] BitMap2Byte(Bitmap bitmap) {
        if (null == bitmap) {
            return null;
        }
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

    protected Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case UPLOAD_START:
                    uploadFileM();
                    break;
                case UPLOAD_OK:
                    mHeadImg.setImageBitmap(m_obj_IconBp);// 上传成功设置头像
                    toasetUtil.showSuccess(R.string.upload_success);
                    break;
                case UPLOAD_ERROR:
                    Toast.makeText(getActivity(), m_str_uploadMsg, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };

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
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
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
//                    showUIDialogState(true);
                    uploadFileM();
                }
            }, "upPic1").start();
        } else {
            toasetUtil.showInfo("网络连接出错,无法上传头像");
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
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
                final String[] selectionArgs = new String[] { split[1] };

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

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

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

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    /******************************************** 修改头像end *****************************************************/



}
