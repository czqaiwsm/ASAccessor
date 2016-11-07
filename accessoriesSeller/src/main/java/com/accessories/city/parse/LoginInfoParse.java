package com.accessories.city.parse;

import android.util.Log;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.utils.URLConstants;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

/**
 * @desc 登录
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class LoginInfoParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        Log.e(">>>>>","json:"+json);

        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());

        if(URLConstants.SUCCESS_CODE.equals(result.getResult())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<UserInfo>>() {
            }.getType());
        }
        return result;
    }

    public static void main(String[] args){

    }

}
