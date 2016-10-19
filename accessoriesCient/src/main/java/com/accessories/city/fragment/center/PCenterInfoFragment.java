package com.accessories.city.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import com.accessories.city.R;
import com.accessories.city.activity.TeacherMainActivity;
import com.accessories.city.activity.center.FeedBackActivity;
import com.accessories.city.activity.center.PCenterInfoUserActivity;
import com.accessories.city.activity.center.ServiceProtocolActivity;
import com.accessories.city.activity.center.SettingActivity;
import com.accessories.city.activity.login.SellerLoginActivity;
import com.accessories.city.activity.teacher.MyAssetActivity;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.LoginInfoParse;
import com.accessories.city.utils.AppManager;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.view.RoundImageView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.HashMap;
import java.util.Map;

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
    private RelativeLayout caution_layout;
    private RelativeLayout feedback_layout;
    private RelativeLayout custom_layout;
    private RelativeLayout setting_layout;

    private UserInfo mUserInfo;
    private TextView account_customname;
    private TextView account_ordername;

    private boolean isPrepare = false;
    private boolean isVisible = false;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserInfo = BaseApplication.getUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pcenter_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                if(BaseApplication.isLogin()){
                    BaseApplication.saveUserInfo(null);
                    BaseApplication.setMt_token("");
                }
                TeacherMainActivity.exit = true;
                AppManager.getAppManager().finishAllActivity();
            }
        });
//        setRightHeadIcon(R.drawable.pc_search_right,new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toClassActivity(PCenterInfoFragment.this,MsgChooseActivity.class.getName());
//            }
//        });
    }


    private void onLazyLoad(){

        if(isPrepare && isVisible){
            requestData(0);
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
        onLazyLoad();
    }

    private void initView(View v) {
        mHeadImg = (RoundImageView) v.findViewById(R.id.account_head_img);
        pcenter_avatar_layout = (RelativeLayout) v.findViewById(R.id.pcenter_avatar_layout);
        wallet_layout = (RelativeLayout) v.findViewById(R.id.wallet_layout);
        order_layout = (RelativeLayout) v.findViewById(R.id.order_layout);
        caution_layout = (RelativeLayout) v.findViewById(R.id.cation_layout);
        feedback_layout = (RelativeLayout) v.findViewById(R.id.feedBace_layout);
        custom_layout = (RelativeLayout) v.findViewById(R.id.custom_layout);
        setting_layout = (RelativeLayout) v.findViewById(R.id.set_layout);
        account_customname = (TextView)v.findViewById(R.id.account_customname);
        account_ordername = (TextView)v.findViewById(R.id.account_ordername);

//        pcenter_avatar_layout.setOnClickListener(this);
        wallet_layout.setOnClickListener(this);
        order_layout.setOnClickListener(this);
        caution_layout.setOnClickListener(this);
        feedback_layout.setOnClickListener(this);
        custom_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);

        setData(mUserInfo);

    }

    private void setData(UserInfo userInfo) {
        account_customname.setText("0791-85232660");
        if(userInfo != null){

            String money = "<span>"+getString(R.string.integeral, userInfo.getIntegral())+"<font color='#0099FF'>(￥"+userInfo.getMoney()+")</font></span>";
            account_ordername.setText(Html.fromHtml(money));
//            ImageLoader.getInstance().displayImage(userInfo.getHeadImg(), mHeadImg, ImageLoaderUtil.mHallIconLoaderOptions);
//            name.setText(userInfo.getNickName());
//            phone.setText(userInfo.getMobile());
//            phone.setText(DataMapConstants.getJoniorMap().get(userInfo.getGrade()));
        }


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.pcenter_avatar_layout:// 头像
                toClassActivity(PCenterInfoFragment.this, PCenterInfoUserActivity.class.getName());
                break;
            case R.id.wallet_layout:// 关于我们
                Intent intent1 = new Intent(mActivity, ServiceProtocolActivity.class);
                intent1.setFlags(12);
                intent1.putExtra("url","www.baidu.com");
                mActivity.startActivity(intent1);
//                toClassActivity(PCenterInfoFragment.this, WalletActivity.class.getName());
                break;
            case R.id.order_layout:// 积分
//                toClassActivity(PCenterInfoFragment.this, OrderActivity.class.getName());
                break;
            case R.id.custom_layout:// 联系我们
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+account_customname.getText().toString()));
                startActivity(intent);
                break;
            case R.id.cation_layout:// 我的评价
                toClassActivity(PCenterInfoFragment.this, MyAssetActivity.class.getName());
                break;

            case R.id.feedBace_layout:// 反馈
                toClassActivity(PCenterInfoFragment.this, FeedBackActivity.class.getName());
                break;

            case R.id.set_layout:// 设置
                toClassActivity(PCenterInfoFragment.this, SellerLoginActivity.class.getName());
                break;

        }

    }

    @Override
    protected void requestData(int requestCode) {
        HttpURL url = new HttpURL();
        Map postParams = new HashMap<String,String>();
        url.setmBaseUrl(URLConstants.GETUSERINFO);
        if(mUserInfo!= null)
        postParams.put("userId", ""+mUserInfo.getId());
        RequestParam param = new RequestParam();
        param.setmParserClassName(LoginInfoParse.class.getName());
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestCode), createMyReqErrorListener(), param);
    }
    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<UserInfo> jsonParserBase = (JsonParserBase<UserInfo>)obj;
        if ((jsonParserBase != null)){
            mUserInfo = jsonParserBase.getObj();
            BaseApplication.saveUserInfo(jsonParserBase.getObj());
            BaseApplication.setMt_token(jsonParserBase.getObj().getId());
            JPushInterface.setAlias(BaseApplication.getInstance(), "t_" + BaseApplication.getUserInfo().getId(), null);
            setData(mUserInfo);
        }
    }

    /******************************************** 修改头像start *****************************************************/
    private RoundImageView mHeadImg;
    private RelativeLayout pcenter_avatar_layout;
    // popupwidos


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setData(mUserInfo);
    }
}
