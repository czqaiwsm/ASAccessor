package com.accessories.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.accessories.seller.R;
import com.accessories.seller.activity.login.LoginActivity;
import com.accessories.seller.bean.SellerInfo;
import com.accessories.seller.bean.UploadBean;
import com.accessories.seller.fragment.TeacherHomePageFragment;
import com.accessories.seller.fragment.center.PCenterInfoFragment;
import com.accessories.seller.fragment.msg.MsgInfoFragment;
import com.accessories.seller.fragment.msg.MsgInfosFragment;
import com.accessories.seller.help.RequsetListener;
import com.accessories.seller.utils.AppLog;
import com.accessories.seller.utils.BaseApplication;
import com.accessories.seller.utils.SmartToast;
import com.accessories.seller.utils.URLConstants;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.download.base.utils.ScreenUtils;
import com.download.update.UpdateMgr;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.util.HashMap;
import java.util.Map;

import io.jchat.android.activity.ConversationListFragment;

/**
 * 老师端主页面
 */
public class TeacherMainActivity extends BaseActivity implements View.OnClickListener,RequsetListener{
    /**
     * Called when the activity is first created.
     */

    // 未读消息textview
    private TextView unreadLabel;
    // 未读通讯录textview
    private TextView unreadAddressLable;

    private Button[] mTabs;
    private Fragment[] fragments;

//    private TeacherHomePageFragment homePageFragment;
//    private MsgInfosFragment msgInfosFragment;
//    private MsgInfosFragment scheduleFragment;
    private PCenterInfoFragment pCenterFragment;

    private final int VIEW_COUNT = 4;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;
    Fragment currentFragment = null;
    int i=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestData(1);
        count++;
        ScreenUtils.getScreenSize(this);
        UpdateMgr.getInstance(this).checkUpdateInfo(null, false);
        setContentView(R.layout.main_activity);
        fragments = new Fragment[VIEW_COUNT];
        fragments[0] = new ConversationListFragment();
        fragments[1] =new MsgInfoFragment();
        fragments[2] = new TeacherHomePageFragment();
        fragments[3] = pCenterFragment = new PCenterInfoFragment();

        initView();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for(int i=0;i<fragments.length;i++){
            transaction.add(R.id.fragment_container,fragments[i]).hide(fragments[i]);

        }
        currentFragment = fragments[0];
        transaction.show(currentFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        mTabs = new Button[VIEW_COUNT];
        mTabs[0] = (Button) findViewById(R.id.btn_setting);
        mTabs[1] = (Button) findViewById(R.id.btn_conversation);
        mTabs[2] = (Button) findViewById(R.id.btn_address_list);
        mTabs[3] = (Button) findViewById(R.id.btn_center);
        // 把第一个tab设为选中状态
        currentTabIndex = 0;
        mTabs[currentTabIndex].setSelected(true);

        for(int i=0;i<mTabs.length;i++){
            mTabs[i].setOnClickListener(this);
        }

    }


    @Override
    public void onClick(View v) {
        int index = 0;
        switch (v.getId()){
            case R.id.btn_setting:
                currentFragment = fragments[0];
                index = 0;
                break;
            case R.id.btn_conversation:
                currentFragment = fragments[1];
                index = 1;
                break;
            case R.id.btn_address_list:
                currentFragment = fragments[2];
                index = 2;
                break;
            case R.id.btn_center:
                currentFragment = showSetting();
                index = 3;
                break;
        }

        if(currentTabIndex != index){
            mTabs[currentTabIndex].setSelected(false);
            mTabs[index].setSelected(true);
            hidShow(currentFragment);
            currentTabIndex = index;
        }
    }

    private void hidShow(Fragment newf){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for(int i=0;i<fragments.length;i++){
            transaction.hide(fragments[i]);
        }
        transaction.show(newf);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示购物车界面
     * 1.未登录
     * 2.已登录，购物车为空
     * 3.已登录，购物车不为空
     */
    private Fragment showShopCar(){
        return null;
    }

    /**
     * @return
     */
    private Fragment showSetting(){
            return pCenterFragment;
    }
    private long firstTime = 0;
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {// 如果两次按键时间间隔大于2秒，则不退出
                SmartToast.makeText(this,
                        getString(R.string.exit), Toast.LENGTH_SHORT).show();
                firstTime = secondTime;// 更新firstTime
                return true;
            } else {
                System.gc();
                System.exit(0);// 否则退出程序
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().locationUitl.stopLocation();
    }

    int count = 0;
    public static boolean exit = false;
    @Override
    public void finish() {
        if(count == 1 && exit) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        exit = false;
        super.finish();
    }

    protected void requestData(int requestCode) {

        HttpURL url = new HttpURL();
        Map postParams = new HashMap<String,String>();
        RequestParam param = new RequestParam();
        switch (requestCode){
            case 1:
                url.setmBaseUrl(URLConstants.SHOPDETAIL);
                postParams.put("shopId",BaseApplication.getSellerUserInfo().getShopId());
                break;

        }
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(this, createReqSuccessListener(requestCode), createMyReqErrorListener(), param);
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

        }

    }


    protected Response.Listener<Object> createReqSuccessListener(final int requestType) {
        return new Response.Listener<Object>() {
            @Override
            public void onResponse(Object object) {
                if (object != null) {
                        System.out.println("respJson=="+object.toString());
                        JsonParserBase jsonParserBase =  ParserUtil.fromJsonBase(object.toString(), new TypeToken<JsonParserBase>() {
                        }.getType());
                        if(jsonParserBase != null && URLConstants.SUCCESS_CODE.equals(jsonParserBase.getResult())){

                            handleRspSuccess(requestType,object.toString());
                        }else{
                            toasetUtil.showInfo( jsonParserBase.getMessage().toString());
                        }
                    }
            }
        };
    }

    protected Response.Listener<Object> createReqSuccessListener() {
        return createReqSuccessListener(0);
    }

    protected Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.Loge(" data failed to load " + error.getMessage());
                    toasetUtil.showErro(R.string.loading_fail_server);
            }
        };
    }


}
