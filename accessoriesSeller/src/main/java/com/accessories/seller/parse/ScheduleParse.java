package com.accessories.seller.parse;

import com.accessories.seller.bean.CourseInfo;
import com.accessories.seller.utils.URLConstants;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @desc 教师详情
 * @creator caozhiqing
 * @data 2016/3/28
 */
public class ScheduleParse implements IParser {
    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getResult())){
            JsonParserBase<ArrayList<ArrayList<CourseInfo>>> base = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<ArrayList<ArrayList<CourseInfo>>>>() {
            }.getType());
            return base;
        }
        return result;
    }


}
