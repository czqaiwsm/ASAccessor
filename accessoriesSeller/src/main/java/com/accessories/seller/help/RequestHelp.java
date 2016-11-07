package com.accessories.seller.help;

import com.accessories.seller.utils.BaseApplication;
import com.accessories.seller.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc 一些接口使用的公共方法
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class RequestHelp {

    public static Map getBaseParaMap(String cmd){
        BaseApplication application = BaseApplication.getInstance();
        Map postParams = new HashMap<String, String>();
        postParams.put("cmd", cmd);
//        postParams.put("userId", application.userId);
//        postParams.put("mobile", application.mobile);
        postParams.put("appVersion", application.appVersion);
        postParams.put("clientType", 3);
        postParams.put("accessToken", BaseApplication.getMt_token());
        postParams.put("deviceId", BaseApplication.diviceId);
        return postParams;
    }

    //1.注册 2.重置密码 3.修改密码 4.手机号绑定
    public static RequestParam getVcodePara(String mobile){
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.GETMSGCODE);

        Map postParams = new HashMap() ;
        postParams.put("phone",mobile);
        RequestParam param = new RequestParam();

        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        return param;
    }



}
