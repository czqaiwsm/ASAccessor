package com.accessories.city.utils;

import android.app.Activity;
import android.os.Handler;
import com.accessories.city.bean.PayInfo;
import com.accessories.city.help.RequestHelp;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.util.Map;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/4/22L
 */
public class PayUtil {


    public static void alipay(Activity mActivity, PayInfo payInfo, Object payCallBack){

//        AlipayUtil alipayUtil = new AlipayUtil(mActivity,payInfo.getOrderNum(),payInfo.getContent(),payInfo.getProductDesc(),payInfo.getPrice(),payCallBack);
//        alipayUtil.alipay();
    }

    public static void walletPay(final Activity mActivity, PayInfo payInfo, final Object payCallBack){
        final WaitLayer waitLayer = new WaitLayer(mActivity, WaitLayer.DialogType.MODALESS);
        waitLayer.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              waitLayer.dismiss();
            }
        },20000);
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = null;
        RequestParam param = new RequestParam();
                postParams = RequestHelp.getBaseParaMap("BalancePay");
                postParams.put("orderCode",payInfo.getOrderNum());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(mActivity, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object o) {
                waitLayer.dismiss();
                JsonParserBase base = ParserUtil.fromJsonBase(o.toString(), new TypeToken<JsonParserBase>() {
                }.getType());
//                if(base.getResult().equalsIgnoreCase(URLConstants.SUCCESS_CODE)){
//                    SmartToast.showText(mActivity,"支付成功");
////                    payCallBack.paySucc();
//                }else {
//                    SmartToast.showText(mActivity,base.getMessage());
//
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SmartToast.showText(mActivity,"支付失败");
                waitLayer.dismiss();
//                payCallBack.payFail();

            }
        }, param);



    }

}
