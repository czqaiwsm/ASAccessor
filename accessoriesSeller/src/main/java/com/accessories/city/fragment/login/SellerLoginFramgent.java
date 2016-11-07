package com.accessories.city.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import com.accessories.city.R;
import com.accessories.city.activity.TeacherMainActivity;
import com.accessories.city.activity.home.PhoneRecordActivity;
import com.accessories.city.activity.login.ForgetPassActivity;
import com.accessories.city.activity.login.RegisterActivity;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.LoginInfoParse;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.PhoneUitl;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.WaitLayer;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc 请用一句话描述此文件
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class SellerLoginFramgent extends BaseFragment implements View.OnClickListener,RequsetListener {

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
        setTitleText("商家登录");
        setLeftHeadIcon(0);
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

        forget_pass_text.setVisibility(View.GONE);
        register_text.setVisibility(View.GONE);
        login_username.setHint("请输入用户名");
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
                requestTask();
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

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.SHOPLOGIN);
        Map postParams = new HashMap();
        postParams.put("account", login_username.getText().toString());
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
            Intent intent = new Intent(mActivity,PhoneRecordActivity.class);
            intent.putExtra("shopId",jsonParserBase.getObj().getShopId());
            startActivity(intent);
            mActivity.finish();
        }
    }

}