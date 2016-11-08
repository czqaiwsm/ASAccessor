package com.accessories.seller.fragment.msg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accessories.seller.R;
import com.accessories.seller.activity.center.WidthdrawInfoActivity;
import com.accessories.seller.adapter.MsgInfoAdpter;
import com.accessories.seller.bean.MsgInfo;
import com.accessories.seller.bean.Value;
import com.accessories.seller.bean.msg.ListBase;
import com.accessories.seller.fragment.PullRefreshFragment;
import com.accessories.seller.help.RequsetListener;
import com.accessories.seller.utils.BaseApplication;
import com.accessories.seller.utils.SmartToast;
import com.accessories.seller.utils.URLConstants;
import com.accessories.seller.utils.WaitLayer;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 信息列表
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class MsgInfoFragment extends PullRefreshFragment implements RequsetListener {



    private boolean prepare = false;
    private boolean isShow = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        prepare = true;
        initAdapater(new MsgInfoAdpter(new SoftReference<Context>(mActivity)));

        setTitleText("信息列表");
        setRightHeadIcon(R.drawable.publis_righter, new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
               if("1".equals(BaseApplication.getSellerUserInfo().getVip())){
                   Intent intent = new Intent(mActivity,WidthdrawInfoActivity.class);
                   intent.setFlags(2);
                   startActivityForResult(intent,100);
               }else {
                   SmartToast.showText("您还不是VIP用户,不能发布!");
               }
            }
        });
        onLoade();

//        onLazyLoad();
    }

    private void onLoade(){
        if(prepare && isShow){
            requestTask(1);
            prepare = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isShow = !hidden;
        if(hidden){
            dismissLoadingDilog();
        }
        onLoade();
    }




    @Override
    protected void requestData(int requestType) {

        HttpURL url = new HttpURL();
        Map postParams = new HashMap();
        RequestParam param = new RequestParam();
        switch (requestType){

            case 1:
                url.setmBaseUrl(URLConstants.MEMBER_MESSAGE_LIST);
                postParams.put("page",pageNo+"");
                postParams.put("pageSize",pageSize+"");
                postParams.put("cityId",BaseApplication.getInstance().location[1]+"");
                break;
            case 2:
                url.setmBaseUrl(URLConstants.CONSTANT);
                param = new RequestParam();
                postParams.put("type", "integral_type");
                postParams.put("key","cash");
                break;
        }

        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);

    }

    protected List parseList(String json){
        JsonParserBase<ListBase<MsgInfo>> base = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<ListBase<MsgInfo>>>() {
        }.getType());
        return  base != null?base.getObj().getArray():null;
    }

    @Override
    public void handleRspSuccess(int requestType, Object obj) {
        super.handleRspSuccess(requestType, obj);
        switch (requestType){
            case 2:
                JsonParserBase<Value> jsonBase =  ParserUtil.fromJsonBase(obj.toString(), new TypeToken<JsonParserBase<Value>>() {
                }.getType());
                if(jsonBase.getObj().getValue()< Integer.valueOf(BaseApplication.getUserInfo().getIntegral())){
                    Intent intent = new Intent(mActivity,WidthdrawInfoActivity.class);
                    intent.setFlags(2);
                    startActivityForResult(intent,100);
                }else {
                    SmartToast.showText("您的积分不足,不能发布哦!");
                }

                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            requestTask(1);
        }
    }
}