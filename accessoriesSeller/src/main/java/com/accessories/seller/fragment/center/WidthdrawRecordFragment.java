package com.accessories.seller.fragment.center;

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
import com.accessories.seller.adapter.WidthdrawRecordAdpter;
import com.accessories.seller.bean.MsgInfo;
import com.accessories.seller.bean.WidthdrawRecord;
import com.accessories.seller.bean.msg.ListBase;
import com.accessories.seller.fragment.PullRefreshFragment;
import com.accessories.seller.help.RequsetListener;
import com.accessories.seller.utils.BaseApplication;
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
 * @desc 消息--》消息
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class WidthdrawRecordFragment extends PullRefreshFragment implements RequsetListener {



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
        initAdapater(new WidthdrawRecordAdpter(new SoftReference<Context>(mActivity)));
        setLeftHeadIcon(0);
        setTitleText("提现记录");
        requestTask(1);
//        onLazyLoad();
    }

    private void onLoade(){
        if(prepare && isShow){
//            requestTask(1);
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
        url.setmBaseUrl(URLConstants.CASH_LIST);
        Map postParams = new HashMap();
        RequestParam param = new RequestParam();
        postParams.put("userId",BaseApplication.getUserInfo().getId());
        postParams.put("page",pageNo+"");
        postParams.put("pageSize",pageSize+"");
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);

    }

    protected List parseList(String json){
        JsonParserBase<ListBase<WidthdrawRecord>> base = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<ListBase<WidthdrawRecord>>>() {
        }.getType());
        return  base != null?base.getObj().getArray():null;
    }

}