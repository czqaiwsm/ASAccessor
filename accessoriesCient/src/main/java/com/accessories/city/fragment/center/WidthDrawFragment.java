package com.accessories.city.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.city.R;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.bean.VerifyCode;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.fragment.TeacherHomePageFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.BaseParse;
import com.accessories.city.parse.VerifyCodeParse;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.SmartToast;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.WaitLayer;
import com.accessories.city.view.Bec_nurse_Window;
import com.google.gson.internal.LinkedTreeMap;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.Map;

/**
 * 钱包
 * @author czq
 * @time 2015年9月28日上午11:44:26
 */
public class WidthDrawFragment extends BaseFragment implements OnClickListener, RequsetListener{


    @Bind(R.id.alipayAccount)
    EditText alipayAccount;
    @Bind(R.id.alipayName)
    EditText alipayName;
    @Bind(R.id.recharge_query)
    TextView rechargeQuery;

    @Bind(R.id.drawMoney)
    EditText drawMoney;
    @Bind(R.id.register_passCode)
    EditText register_passCode;
    @Bind(R.id.register_getCode)
    TextView register_getCode;

    private String releaName = "";
    private String account   = "";

    private int MSG_TOTAL_TIME;
    private final int MSG_UPDATE_TIME = 500;
    private VerifyCode verifyCode;
    private int balance;
    UserInfo userInfo = BaseApplication.getUserInfo();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null){
            balance = intent.getIntExtra("balance",0);
            releaName = intent.getStringExtra("releaName")   ;
            account   = intent.getStringExtra("account")     ;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widthdraw, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        setLoadingDilog(WaitLayer.DialogType.MODALESS);
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText("提现");
        setLeftHeadIcon(0);

        rechargeQuery.setOnClickListener(this);
        register_getCode.setOnClickListener(this);
        if(TeacherHomePageFragment.homeInfo != null){
//            String temp = TeacherHomePageFragment.homeInfo.getAlipay();
//            alipayAccount.setText(TextUtils.isEmpty(temp)?"":temp);
//            temp = TeacherHomePageFragment.homeInfo.getRealName();
//            alipayName.setText(TextUtils.isEmpty(temp)?"":temp);
        }
        alipayName.setText(releaName);
        alipayAccount.setText(account);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.register_getCode:
                register_getCode();
                break;
            case R.id.recharge_query:

                if (TextUtils.isEmpty(alipayAccount.getText().toString()) || TextUtils.isEmpty(alipayName.getText().toString())) {
//                    toasetUtil.showInfo("请输入账号和姓名!");
                    SmartToast.showText("请输入账号和姓名!");
                }else
                if (TextUtils.isEmpty(drawMoney.getText().toString())) {
//                    toasetUtil.showInfo("请输入提现金额!");
                    SmartToast.showText("请输入提现金额!");
                }else
                if (TextUtils.isEmpty(register_passCode.getText().toString()) || verifyCode==null) {
//                    toasetUtil.showInfo("获取验证码!");
                    SmartToast.showText("获取验证码!");

                }else if(verifyCode == null || !verifyCode.getMsgCode().equalsIgnoreCase(register_passCode.getText().toString())){
//                    toasetUtil.showInfo("请输入正确的验证码!");
                    SmartToast.showText("请输入正确的验证码!");

                }
                else if(Integer.valueOf(drawMoney.getText().toString())<=0){
//                    toasetUtil.showInfo("提现金额不能小于0");
                    SmartToast.showText("提现金额不能小于0");

                }else if(Integer.valueOf(drawMoney.getText().toString())> balance){
//                    toasetUtil.showInfo("超出账号余额!");
                    SmartToast.showText("超出账号余额!");

                }
                else {
                    requestTask(1);
                }
                break;
            case R.id.commit:
                mActivity.setResult(Activity.RESULT_OK);
                mActivity.finish();
                break;
        }

    }


    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = null;
        RequestParam param = new RequestParam();
        
        switch (requestType){
            
            case  1:
                postParams = RequestHelp.getBaseParaMap("Withdraw");
                postParams.put("realName", alipayName.getText().toString());
                postParams.put("account", alipayAccount.getText().toString());
                postParams.put("price", drawMoney.getText().toString());
//                postParams.put("sendId",verifyCode.getSendId());
                param.setmParserClassName(BaseParse.class.getName());
                param.setmPostarams(postParams);
                break;
            case  2:
//                param = RequestHelp.getVcodePara("VCode",userInfo.getMobile(),4);
//				param.setmParserClassName(VerifyCodeParse.class.getName());
                param.setmParserClassName(VerifyCodeParse.class.getName());
                break;
        }
//        postParams.put("price", BaseApplication.getUserInfo() != null?
//                BaseApplication.getUserInfo().getBalance():"");
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);

    }


//    @Override
//    public void paySucc() {
//        mActivity.setResult(Activity.RESULT_OK);
//        mActivity.finish();
//    }
//
//    @Override
//    public void payFail() {
//
//    }

    /**
     *
     * 获取验证码
     */
    private void register_getCode() {
//        if(userInfo == null || TextUtils.isEmpty(userInfo.getMobile())){
//            SmartToast.makeText(mActivity, R.string.input_error, Toast.LENGTH_SHORT).show();
//        }
//        if (TextUtils.isEmpty(userInfo.getMobile())) {
//            SmartToast.makeText(mActivity, R.string.input_error, Toast.LENGTH_SHORT).show();
//        } else {
//            if (!PhoneUitl.isPhone(userInfo.getMobile())) {
//                SmartToast.makeText(mActivity, R.string.phone_error, Toast.LENGTH_SHORT).show();
//            } else {
//                register_getCode.setEnabled(false);
//                MSG_TOTAL_TIME = 60;
//                // Toast.makeText(mActivity, "短信已发送，请稍候！",
//                // Toast.LENGTH_SHORT).show();
//                Message message = new Message();
//                message.what = MSG_UPDATE_TIME;
//                timeHandler.sendMessage(message);
//                requestData(2);// ----------发送请求
//                register_getCode.requestFocus();
//            }
//        }
    }

    // 验证码倒计时
    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if(isDetached()){
                return;
            }
            switch (msg.what) {
                case MSG_UPDATE_TIME:
                    MSG_TOTAL_TIME--;
                    if (MSG_TOTAL_TIME > 0) {
                        register_getCode.setText(String.format(getResources().getString(R.string.has_minuter,MSG_TOTAL_TIME+"")));
                        Message message = obtainMessage();
                        message.what = MSG_UPDATE_TIME;
                        sendMessageDelayed(message, 1000);
                    } else if (MSG_TOTAL_TIME < -10) {
                        register_getCode.setText(R.string.addcode_resend);
                        register_getCode.setEnabled(true);
                    } else {
                        register_getCode.setText(R.string.addcode_code);
                        register_getCode.setEnabled(true);
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private Bec_nurse_Window becNursePop ;
    @Override
    public void handleRspSuccess(int requestType, Object obj) {
        switch (requestType){
            case  1:
                JsonParserBase jsonParserBase = (JsonParserBase) obj;
                if ((jsonParserBase != null)) {
                    LinkedTreeMap<String, String> treeMap = (LinkedTreeMap<String, String>) jsonParserBase.getObj();
                    String order = treeMap.get("tips");
                    if(becNursePop == null){
                        becNursePop = new Bec_nurse_Window(mActivity,register_getCode,this);
                    }
                    becNursePop.showAtLocation(register_getCode, Gravity.CENTER, 0, 0);
                    becNursePop.setContent(order);
                }
                break;
            case  2:
                MSG_TOTAL_TIME = -1;
                JsonParserBase<VerifyCode> jsonParserBase1 = (JsonParserBase<VerifyCode>)obj;
                verifyCode = jsonParserBase1.getObj();
                toasetUtil.showInfo("信息已发送!");
//                register_passCode.setText(verifyCode !=null?verifyCode.getMsgCode():"");
                break;
            
            
        }
        
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){

        }
    }
}
