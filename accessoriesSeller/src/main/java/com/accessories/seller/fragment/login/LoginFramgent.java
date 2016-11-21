package com.accessories.seller.fragment.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import io.jchat.android.chatting.utils.FileHelper;
import io.jchat.android.chatting.utils.HandleResponseCode;
import io.jchat.android.chatting.utils.SharePreferenceManager;

import com.accessories.seller.R;
import com.accessories.seller.activity.TeacherMainActivity;
import com.accessories.seller.activity.login.ForgetPassActivity;
import com.accessories.seller.activity.login.RegisterActivity;
import com.accessories.seller.bean.SellerUserInfo;
import com.accessories.seller.bean.UserInfo;
import com.accessories.seller.fragment.BaseFragment;
import com.accessories.seller.help.RequsetListener;
import com.accessories.seller.parse.LoginInfoParse;
import com.accessories.seller.utils.BaseApplication;
import com.accessories.seller.utils.PhoneUitl;
import com.accessories.seller.utils.URLConstants;
import com.accessories.seller.utils.Utils;
import com.accessories.seller.utils.WaitLayer;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc 请用一句话描述此文件
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class LoginFramgent extends BaseFragment implements View.OnClickListener,RequsetListener {

    private EditText login_username;
    private EditText login_pass;
    private TextView forget_pass_text;
    private TextView login_text;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pcenter_login,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLoadingDilog(WaitLayer.DialogType.MODALESS);
        setTitleText(R.string.login_title);
        initView(view);
    }
    private void initView(View view){
        login_username = (EditText)view.findViewById(R.id.login_username);
        login_pass = (EditText)view.findViewById(R.id.login_pass);
        forget_pass_text = (TextView)view.findViewById(R.id.forget_pass_text);
        login_text = (TextView)view.findViewById(R.id.login_text);

        login_text.setOnClickListener(this);
        forget_pass_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.login_text:

//              String json = "{\"respCode\":200,\"respDesc\":\"\",\"respScore\":0,\"respCoin\":0,\"serviceTime\":\"2016-05-23 14:28:02\",\"data\":{\"auditStatus\":2,\"idcardInfo\":[],\"auditInfo\":{\"imgUrl\":\"http://120.25.171.4:80/learn-resource/rest/bz\",\"profession\":\"那个那个看过\",\"college\":\"那个那个看过\",\"education\":2},\"idcardStatus\":1}}";
//              try {
//                  JSONObject jsonObject = new JSONObject(json);
//                  jsonObject = jsonObject.getJSONObject("data");
//                  if(jsonObject.isNull("idcardInfo")) {
////                jsonObject = jsonObject.getJSONObject("idcardInfo");
//                      System.out.println("is null");
//                  }
//              } catch (JSONException e) {
//                  e.printStackTrace();
//              }
              if(TextUtils.isEmpty(login_username.getText()) || TextUtils.isEmpty(login_pass.getText())){
                  toasetUtil.showInfo( R.string.passname_empty);
              }else {
                  showLoadingDilog("");
                  JMessageClient.register(login_username.getText().toString(), login_username.getText().toString(), new BasicCallback() {

                      @Override
                      public void gotResult(final int status, final String desc) {
                          if (status == 0 || status ==1002 || status==898001) {//极光注册成功
                              loginIm();
                          } else {
                              dismissLoadingDilog();
                              HandleResponseCode.onHandle(mActivity, status, false);
                          }
                      }
                  });
//                  requestTask();
              }
              break;
          case R.id.forget_pass_text:
              toClassActivity(this, ForgetPassActivity.class.getName());
              break;
          default:break;
      }

    }

    private void loginIm(){
        JMessageClient.login(login_username.getText().toString(), login_username.getText().toString(), new BasicCallback() {
            @Override
            public void gotResult(final int status, final String desc) {
                if (status == 0) {
                    requestData(0);
                } else {
                    dismissLoadingDilog();
                    Log.i("LoginController", "status = " + status);
                    HandleResponseCode.onHandle(mActivity, status, false);
                }
            }
        });
    }


    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.SHOPLOGIN);
        Map postParams = new HashMap();

        postParams.put("account", login_username.getText().toString());
        postParams.put("pwd",login_pass.getText().toString());
        RequestParam param = new RequestParam();
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(),createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<SellerUserInfo> jsonParserBase =  ParserUtil.fromJsonBase(obj.toString(), new TypeToken<JsonParserBase<SellerUserInfo>>() {
        }.getType());

        SellerUserInfo sellerUserInfo = null;

        if ((sellerUserInfo = jsonParserBase.getObj()) != null){
            BaseApplication.saveSellerUserInfo(sellerUserInfo);
            toClassActivity(LoginFramgent.this, TeacherMainActivity.class.getName());//老师


            final cn.jpush.im.android.api.model.UserInfo myUserInfo = JMessageClient.getMyInfo();
            myUserInfo.setNickname(BaseApplication.getSellerUserInfo().getShopName());
            JMessageClient.updateMyInfo(cn.jpush.im.android.api.model.UserInfo.Field.nickname, myUserInfo, new BasicCallback() {
                @Override
                public void gotResult(final int status, final String desc) {
                    if (status == 0) {
                        Log.i("IMInfo","nickName"+myUserInfo.getNickname()+" 更新成功");
                    } else {
                        Log.i("IMError","nickName"+myUserInfo.getNickname()+" 更新失败");
//                        dismissLoadingDilog();
//                        HandleResponseCode.onHandle(mActivity, status, false);
                    }
                }
            });

            ImageLoader.getInstance().displayImage(BaseApplication.getSellerUserInfo().getShopPic(), new ImageView(mActivity), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {


                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    String temp =  Utils.saveBitmap2file(bitmap,"im_chat_head.jpg");
                    if(TextUtils.isEmpty(temp)) {
                        Log.e("error:","头像本地化失败");
                        return;
                    }
                    JMessageClient.updateUserAvatar(new File(temp), new BasicCallback() {
                        @Override
                        public void gotResult(final int status, final String desc) {
                            if (status == 0) {
                                Log.i("IMInfo","头像"+BaseApplication.getUserInfo().getUserHead()+" 更新成功");
                            } else {
                                Log.i("IMError","头像"+BaseApplication.getUserInfo().getUserHead()+" 更新失败");
//                        dismissLoadingDilog();
//                        HandleResponseCode.onHandle(mActivity, status, false);
                            }
                        }
                    });
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });

            mActivity.finish();
        }
    }

    @Override
    protected void errorRespone() {
        super.errorRespone();
        logoutIM();
    }

    @Override
    protected void failRespone() {
        super.failRespone();
        logoutIM();
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

}