package com.accessories.city.bean;

import android.content.Context;
import com.accessories.city.R;
import com.accessories.city.utils.BaseApplication;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc
 * @creator caozhiqing
 * @data 2016/4/1
 */
public class DataMapConstants {

    private static Map<String,String> coursesMap ;
    private static Map<String,String> joniorMap;
    private static Map<String,String> gender;
    private static Map<String,String> degree;//学历

    /**
     * 获取课程
     * @returnc
     */
    public static Map<String,String> getCourse(){
        if(coursesMap == null || coursesMap.size()==0){
            coursesMap = new HashMap<String, String>();

            Context context = BaseApplication.getInstance().getApplicationContext();
            String[] ids = context.getResources().getStringArray(R.array.course_id);
            String[] values = context.getResources().getStringArray(R.array.course);
            for(int i=0;i<ids.length;i++){
                coursesMap.put(ids[i],values[i]);
            }
        }
        return coursesMap;
    }

    /**
     * 年级
     * @return
     */
    public static Map<String,String> getJoniorMap(){
        if(joniorMap == null){
            joniorMap = new HashMap<String, String>();
            Context context = BaseApplication.getInstance().getApplicationContext();
            String[] joniorIds = context.getResources().getStringArray(R.array.jonior_id);
            String[] joniorS = context.getResources().getStringArray(R.array.jonior);
            for(int i=0;i<joniorIds.length;i++){
                joniorMap.put(joniorIds[i],joniorS[i]);
            }
        }
        return joniorMap;

    }

    public static Map<String,String> getGender(){
        if(gender == null){
            gender = new HashMap<String, String>();
            gender.put("1","男");
            gender.put("2","女");
        }
        return gender;
    }

    public static Map<String, String> getDegree(){
        if(degree == null){
            degree = new HashMap<String, String>();
            Context context = BaseApplication.getInstance().getApplicationContext();
            String[] ids = context.getResources().getStringArray(R.array.degree_id);
            String[] values = context.getResources().getStringArray(R.array.degree);
            for(int i=0;i<ids.length;i++){
                degree.put(ids[i],values[i]);
            }
        }
        return degree;

    }

    private static Map<String,String> frequency;//上课频次

    public static Map<String, String> getFrequency(){
        if(frequency == null){
            frequency = new HashMap<String, String>();
            Context context = BaseApplication.getInstance().getApplicationContext();
            String[] ids = context.getResources().getStringArray(R.array.frequency_id);
            String[] values = context.getResources().getStringArray(R.array.frequency);
            for(int i=0;i<ids.length;i++){
                frequency.put(ids[i],values[i]);
            }
        }
        return frequency;

    }

    public static void main(String args[]){

        String json = "{\"respCode\":200,\"respDesc\":\"\",\"respScore\":0,\"respCoin\":0,\"serviceTime\":\"2016-05-23 14:28:02\",\"data\":{\"auditStatus\":2,\"idcardInfo\":[],\"auditInfo\":{\"imgUrl\":\"http://120.25.171.4:80/learn-resource/rest/bz\",\"profession\":\"那个那个看过\",\"college\":\"那个那个看过\",\"education\":2},\"idcardStatus\":1}}";
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonObject = jsonObject.getJSONObject("data");
            jsonObject = jsonObject.getJSONObject("idcardInfo");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
