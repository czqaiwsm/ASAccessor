package com.accessories.city.fragment.center;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accessories.city.R;
import com.accessories.city.bean.UploadBean;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.AlertDialogUtils;
import com.accessories.city.utils.AppLog;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.ImageFactory;
import com.accessories.city.utils.NetUtils;
import com.accessories.city.utils.PhoneUitl;
import com.accessories.city.utils.SDCardUtils;
import com.accessories.city.utils.SmartToast;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.Utils;
import com.accessories.city.utils.WaitLayer;
import com.accessories.city.view.AddPopwindow;
import com.accessories.city.view.UpdateAvatarPopupWindow;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.ParserUtil;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.im.android.eventbus.EventBus;

/**
 * @desc 教师首页
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class PublisFragment extends BaseFragment implements View.OnClickListener,RequsetListener {


    @Bind(R.id.contentEt)
    EditText contentEt;
    @Bind(R.id.workLog1Img)
    ImageView workLog1Img;
    @Bind(R.id.workLog1Rl)
    RelativeLayout workLog1Rl;
    @Bind(R.id.workLog2Img)
    ImageView workLog2Img;
    @Bind(R.id.workLog2Rl)
    RelativeLayout workLog2Rl;
    @Bind(R.id.addRl)
    RelativeLayout addRl;
    @Bind(R.id.addessLl)
    RelativeLayout addessLl;
    @Bind(R.id.phoneEt)
    EditText phoneEt;
    @Bind(R.id.addressTv)
    TextView addressTv;

    private String cashType = "-1";//0供应 1求购
    private Bitmap m_obj_IconBp = null;

    private AddPopwindow popwindow = null;

    UpdateAvatarPopupWindow m_obj_menuWindow ;
    private static final int REQUEST_PHOTO = 4;// 相册选择头像
    private static final int REQUEST_CAMEIA = 5;// 相机选择头像
    private static final int RESULT_REQUEST = 6;// 裁剪的结果
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "avatar.jpg";

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
        EventBus.getDefault().register(this);
        Bundle bundle = null;
        if((bundle = getArguments()) != null){
            cashType = bundle.getString("cashType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_publish, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        popwindow = new AddPopwindow(mActivity,this);
    }

    private void initView() {
        addRl.setOnClickListener(this);
        workLog1Rl.setOnClickListener(this);
        workLog2Rl.setOnClickListener(this);
        addessLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.addRl:
                photoSet();
                break;
            case R.id.workLog1Rl:
                AlertDialogUtils.displayMyAlertChoice(mActivity, "提示", "是否确定删除此图片?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        workLog1Rl.setVisibility(View.GONE);
                        workLog1Rl.setTag("");
                        addRl.setVisibility(View.VISIBLE);
                    }
                },null);
                break;
            case R.id.workLog2Rl:
                AlertDialogUtils.displayMyAlertChoice(mActivity, "提示", "是否确定删除此图片?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        workLog2Rl.setVisibility(View.GONE);
                        workLog2Rl.setTag("");
                        addRl.setVisibility(View.VISIBLE);
                    }
                },null);

                break;
            case R.id.addessLl:
                popwindow.payPopShow(v);

                break;
            case R.id.sureBtn:
                addressTv.setText(v.getTag().toString());
                break;
            default:
                break;
        }
    }

    public void onEvent(Integer req){
        if(cashType.equals(req+"")){
            if (!PhoneUitl.isPhone(phoneEt.getText().toString())){
                toasetUtil.showInfo(R.string.phone_error);
                return;
            }
            if(TextUtils.isEmpty(contentEt.getText().toString())){
                toasetUtil.showInfo("请输入内容");
                return;
            }
            if(workLog1Rl.getVisibility() == View.GONE && workLog2Rl.getVisibility() == View.GONE ){
                toasetUtil.showInfo("请选择图片");
                return;
            }
            requestTask(1);
        }

    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.ADD_MEMBER_MESSAGE);
        Map postParams = new HashMap();
        postParams.put("userId", BaseApplication.getUserInfo().getId());
        postParams.put("msgType",cashType);
        postParams.put("flag","0");
        postParams.put("msgContent",contentEt.getText().toString());
        postParams.put("phone",phoneEt.getText().toString());
        postParams.put("address",addressTv.getText().toString());
        postParams.put("cityId",BaseApplication.getInstance().location[1]);

        String tempPath = workLog1Rl.getVisibility() == View.VISIBLE?workLog1Rl.getTag().toString()+",":"";
        tempPath += workLog2Rl.getVisibility() == View.VISIBLE?workLog2Rl.getTag().toString():"";

        postParams.put("msgPic",tempPath);
        RequestParam param = new RequestParam();
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(),createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        SmartToast.showText("发布成功");
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
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
                        100);
            }
//
        } else {
            //已经拥有权限进行拨打
            photoPpw();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                photoPpw();
            } else {
                // Permission Denied
                SmartToast.showText("您拒绝了SD卡访问权限!");
            }
            return;
        }
    }

    private void photoPpw() {
        m_obj_menuWindow = new UpdateAvatarPopupWindow(getActivity(), addRl, itemsOnClick);
        m_obj_menuWindow.showAtLocation(addRl, Gravity.BOTTOM, 0, 0);
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
                    if(workLog1Rl.getVisibility() == View.GONE){
                        workLog1Img.setImageBitmap(m_obj_IconBp);
                        workLog1Rl.setVisibility(View.VISIBLE);
                        workLog1Rl.setTag(result.getFilePath());
                    }else {
                        workLog2Img.setImageBitmap(m_obj_IconBp);
                        workLog2Rl.setVisibility(View.VISIBLE);
                        workLog2Rl.setTag(result.getFilePath());
                    }

                    if(workLog1Rl.getVisibility() == View.VISIBLE
                            && workLog2Rl.getVisibility() == View.VISIBLE){
                        addRl.setVisibility(View.GONE);

                    }

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
        bitmap = ImageFactory.ratio(bitmap,800,800);
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



}