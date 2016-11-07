package com.accessories.city.utils;

import android.text.TextUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;

/**
 * @desc json解析工具类，避免出现非空
 * @creator caozhiqing
 * @data 2016/4/6
 */
public class JSONParseUtil {

    JSONObject jsonObject = null;

    public JSONParseUtil(String jsonStr){
        if(!TextUtils.isEmpty(jsonStr)){
            try {
                jsonObject = new JSONObject(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public String getString(String key){
        if(!TextUtils.isEmpty(key) && (jsonObject != null && jsonObject.has(key))){
            return jsonObject.optString(key);
        }
        return "";
    }

   public static void main(String[] arg){

//    String json = "{id=0.0}";
    String json = "{\"respCode\":200,\"respDesc\":\"\",\"respScore\":0,\"respCoin\":0,\"serviceTime\":\"2016-04-06 03:17:04\",\"data\":{\"id\":0,\"imgPath\":\"http://120.25.171.4:80/learn-resource/rest/7w\"}}";
       JsonObject jsonObject = null;
       try {

           JsonParser jsonParser = new JsonParser();
           jsonObject = jsonParser.parse(new JsonReader(new StringReader(json))).getAsJsonObject();
           System.out.println(jsonObject.get("id"));
//           System.out.println(jsonObject.get("imgPath"));
       } catch (Exception e) {
           e.printStackTrace();
       }

   }
}
