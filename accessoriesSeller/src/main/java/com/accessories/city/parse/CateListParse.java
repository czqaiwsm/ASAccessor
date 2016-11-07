package com.accessories.city.parse;

import com.accessories.city.bean.CateListBean;
import com.accessories.city.utils.URLConstants;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

/**
 * @desc 教师详情
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class CateListParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getResult())){
            JsonParserBase<CateListBean> base = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<CateListBean>>() {
            }.getType());
            return base;
        }
        return result;
    }


}
