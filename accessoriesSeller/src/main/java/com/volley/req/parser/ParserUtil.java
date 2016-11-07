package com.volley.req.parser;

import android.text.TextUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @desc GSON 解析的封装
 * @creator caozhiqing
 * @data 2015/8/20
 */
public class ParserUtil {

    private static Gson gson = new Gson();


    /**
     *
     * @param params
     * @return
     */
    public static String initParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder("");
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=");
            if (!TextUtils.isEmpty(value)) {
                try {
                    sb.append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 最基础的json解析
     * @param json 要解析的json字符串
     * @param type 对应的类型
     * @param <T>
     * @return
     */
    public static <T extends Object> T fromJson(String json,Type type){
        if(json == null || "".equalsIgnoreCase(json) || type == null){
            return null;
        }
        if(gson == null){
            gson = new Gson();
        }
        T returnResult = null;
        try {
            returnResult = gson.fromJson(json, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  returnResult;
    }

    /**
     * 专为JsonParserBase设计解析
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T extends Object> JsonParserBase<T> fromJsonBase(String json,Type type){
        JsonParserBase<T> returnResult = fromJson(json, type);
        return returnResult;
    }

    public static <T extends Object> String toJson(T entity){

        if(entity == null){
            return "";
        }
        String result = "";
        if(entity instanceof String){
            result = (String)entity;
        }else {
            result = gson.toJson(entity);
        }
        return result;
    }
}
