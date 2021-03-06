package com.accessories.city.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.accessories.city.R;
import com.accessories.city.activity.center.WidthdrawInfoActivity;
import com.accessories.city.activity.home.ChooseActivity;
import com.accessories.city.bean.CertifyStatus;
import com.accessories.city.bean.FloatValue;
import com.accessories.city.bean.Value;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.AlertDialogUtils;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.SmartToast;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.Utils;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import cn.jpush.im.android.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @DESC 教师首页
 * @CREATOR CAOZHIQING
 * @DATA 2016/3/10
 */
public class WidthdrawInfoFragment extends BaseFragment implements View.OnClickListener, RequsetListener, IParser {


    @Bind(R.id.accountEt)
    EditText accountEt;
    @Bind(R.id.accountNameEt)
    EditText accountNameEt;
    @Bind(R.id.scoreEt)
    EditText scoreEt;
    @Bind(R.id.login_text)
    TextView loginText;
    private CertifyStatus certifyStatus;

    private String cashType = "-1";//0支付宝 1微信

    FloatValue floatValue = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = null;
        if((bundle = getArguments()) != null){
            cashType = bundle.getString("cashType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_widthdraw_info, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginText.setOnClickListener(this);
        initView();
    }

    private void initView() {
        if("1".equals(cashType)){
            accountEt.setHint("请输入微信账号");
            accountNameEt.setHint("请输入微信对应的姓名");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_text:
                if(TextUtils.isEmpty(accountEt.getText().toString())
                        || TextUtils.isEmpty(accountNameEt.getText().toString())
                        || TextUtils.isEmpty(scoreEt.getText().toString())){
                    SmartToast.showText("请完善提现信息!");
                    return;
                }
                 int integral = Integer.valueOf(scoreEt.getText().toString());
                if(integral<URLConstants.WIDTHDRAW_INTEGRAL){
                    SmartToast.showText("体现积分较小,请输入较多积分!");
                    return;
                }
                if(integral>Integer.valueOf(BaseApplication.getUserInfo().getIntegral())){
                    SmartToast.showText("您的积分不足,请输入较小积分!");
                    return;
                }

                if(floatValue == null){
                    requestTask(2);
                }else {
                    alertDraw();
                }
                break;
        }
    }

    @Override
    protected void requestData(int requestType) {

        HttpURL url = new HttpURL();
        Map postParams = new HashMap();
        RequestParam param = new RequestParam();
        switch (requestType){
            case 1:
                url.setmBaseUrl(URLConstants.ADD_CASH);
                postParams.put("userId", BaseApplication.getUserInfo().getId());
                postParams.put("cashType",cashType);
                postParams.put("cashAccount",accountEt.getText().toString());
                postParams.put("cashName",accountNameEt.getText().toString());
                postParams.put("cashIntegral",scoreEt.getText().toString());
                postParams.put("cashMoney","1000");
                break;
            case 2:
                url.setmBaseUrl(URLConstants.CONSTANT);
                param = new RequestParam();
                postParams.put("type", "exchange_ratio");
                postParams.put("key","1");
                break;
        }

        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType, Object obj) {
        switch (requestType){
            case 1:
                SmartToast.showText("提现成功!");
                EventBus.getDefault().post(BaseApplication.getUserInfo());
                mActivity.finish();
                break;
            case 2:
                JsonParserBase<FloatValue> jsonBase =  ParserUtil.fromJsonBase(obj.toString(), new TypeToken<JsonParserBase<FloatValue>>() {
                }.getType());
                floatValue = jsonBase.getObj();
                if(floatValue != null && !TextUtils.isEmpty(floatValue.getValue()+"")){
                    alertDraw();
                }else {
                    floatValue = null;
                }
                break;
        }

    }

    private void alertDraw(){

        AlertDialogUtils.displayMyAlertChoice(mActivity, "温馨提示", "您提现的积分可兑换" +
                Utils.floatDecimla((Double.valueOf(scoreEt.getText().toString())*floatValue.getValue())+"",null) + "元,是否确认兑换?"
                , new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestTask(1);
                    }
                }, null);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if (URLConstants.SUCCESS_CODE.equals(result.getResult())) {
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<CertifyStatus>>() {
            }.getType());
        }
        return result;
    }


}