package com.accessories.city.fragment.login;

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

import com.accessories.city.R;
import com.accessories.city.activity.TeacherMainActivity;
import com.accessories.city.activity.login.ForgetPassActivity;
import com.accessories.city.activity.login.RegisterActivity;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.LoginInfoParse;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.ImageLoaderUtil;
import com.accessories.city.utils.PhoneUitl;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.Utils;
import com.accessories.city.utils.WaitLayer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.io.File;
import java.net.URI;
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
    private TextView register_text;




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
        register_text = (TextView)view.findViewById(R.id.register_text);

        login_text.setOnClickListener(this);
        register_text.setOnClickListener(this);
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
              }else if (!PhoneUitl.isPhone(login_username.getText().toString())){
                  toasetUtil.showInfo(R.string.phone_error);
              }else {
                  showLoadingDilog("");
                  loginIm();
//                  JMessageClient.register(login_username.getText().toString(), login_username.getText().toString(), new BasicCallback() {
//
//                      @Override
//                      public void gotResult(final int status, final String desc) {
//                          if (status == 0 || status ==1002 || status==898001) {//极光注册成功
//                              loginIm();
//                          } else {
//                              dismissLoadingDilog();
//                              HandleResponseCode.onHandle(mActivity, status, false);
//                          }
//                      }
//                  });
              }
              break;
          case R.id.register_text:
              toClassActivity(this, RegisterActivity.class.getName());
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
        url.setmBaseUrl(URLConstants.LOGIN);
        Map postParams = new HashMap();

        postParams.put("phone", login_username.getText().toString());
        postParams.put("pwd",login_pass.getText().toString());
        RequestParam param = new RequestParam();
//        param.setmParserClassName(LoginInfoParse.class.getName());
        param.setmParserClassName(LoginInfoParse.class.getName());
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(),createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<UserInfo> jsonParserBase = (JsonParserBase<UserInfo>)obj;
        if ((jsonParserBase != null)){
            try{

            BaseApplication.saveUserInfo(jsonParserBase.getObj());
            BaseApplication.setMt_token(jsonParserBase.getObj().getId());
            toClassActivity(LoginFramgent.this, TeacherMainActivity.class.getName());//老师
            JPushInterface.setAlias(BaseApplication.getInstance(),"t_"+BaseApplication.getUserInfo().getId(),null);

            final cn.jpush.im.android.api.model.UserInfo myUserInfo = JMessageClient.getMyInfo();
            myUserInfo.setNickname(BaseApplication.getUserInfo().getNickname());
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

            ImageLoader.getInstance().displayImage(BaseApplication.getUserInfo().getUserHead(), new ImageView(mActivity), new ImageLoadingListener() {
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
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mActivity.finish();
            }

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