package com.accessories.city.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.accessories.city.R;
import com.accessories.city.bean.CertifyStatus;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.URLConstants;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import org.json.JSONObject;

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

    private String cashType = "-1";
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
//        initView();
    }

    private void initView() {
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
        }

    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("QueryCertifi");
        RequestParam param = new RequestParam();
//        param.setmParserClassName(this);
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType, Object obj) {
        JsonParserBase<CertifyStatus> jsonParserBase = (JsonParserBase<CertifyStatus>) obj;
        certifyStatus = jsonParserBase.getObj();
        String satus = certifyStatus.getIdcardStatus();
        initView();

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