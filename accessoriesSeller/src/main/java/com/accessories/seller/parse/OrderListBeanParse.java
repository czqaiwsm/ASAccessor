package com.accessories.seller.parse;

import com.accessories.seller.bean.OrderListBean;
import com.accessories.seller.utils.URLConstants;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

/**
 * @desc 验证码
 * @creator caozhiqing
 * @data 2016/3/28
 */
public  class  OrderListBeanParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }


    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getResult())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<OrderListBean>>() {
            }.getType());
        }
        return result;
    }


    public static void main(String args[]){

    }


}
