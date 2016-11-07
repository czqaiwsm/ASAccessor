package com.accessories.city.fragment.center;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.BaseParse;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.WaitLayer;
import com.google.gson.internal.LinkedTreeMap;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.Map;

/**
 *钱包
 * @author czq
 * @time 2015年9月28日上午11:44:26
 *
 */
public class RechargeFragment extends BaseFragment implements OnClickListener ,RequsetListener {

    private EditText rechargePrice ;
    private TextView rechareQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView(view);
        setLoadingDilog(WaitLayer.DialogType.MODALESS);
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText("充值");
        setLeftHeadIcon(0);

    }

    private void initView(View v) {
        rechargePrice = (EditText)v.findViewById(R.id.recharge_price);
        rechareQuery = (TextView)v.findViewById(R.id.recharge_query);
        rechareQuery.setOnClickListener(this);

    }


    private Intent intent ;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.recharge_query:
                 if(TextUtils.isEmpty(rechargePrice.getText()) || TextUtils.equals("0",rechargePrice.getText())){
                     toasetUtil.showInfo("请输入金额");
                 }else {
                     requestTask();
                 }
                break;
        }

    }

    /**
     * 重新登录
     */
    private void reLogin() {
//        startActivityForResult(new Intent(getActivity(), LoginActivity.class), Constant.RELOGIN);
    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("Recharge") ;

        RequestParam param = new RequestParam();

        postParams.put("payPrice",rechargePrice.getText().toString());
        postParams.put("payType","1");
        param.setmParserClassName(BaseParse.class.getName());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(),createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase jsonParserBase = (JsonParserBase)obj;
        if ((jsonParserBase != null)){
            LinkedTreeMap <String,String> treeMap = (LinkedTreeMap<String, String>) jsonParserBase.getObj();
             String order = treeMap.get("orderCode");
//            PayUtil.alipay(mActivity,new PayInfo(order,rechargePrice.getText().toString(),"充值","充值"),this);
//            BaseApplication.getUserInfo() = jsonParserBase.getObj().getUserInfo();
//            BaseApplication.getInstance().accessToken = jsonParserBase.getObj().getToken();
//            BaseApplication.getInstance().userId = BaseApplication.getUserInfo().getId();
//            toClassActivity(LoginFramgent.this, MainActivity.class.getName());//学生
//            toClassActivity(LoginFramgent.this, TeacherMainActivity.class.getName());//老师

        }
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
}
