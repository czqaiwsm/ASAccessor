package com.accessories.city.bean;

import com.accessories.city.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author czq
 * @desc 日期
 * @date 16/5/4
 */
public class DateBean {

    static  Locale l = new Locale("en");
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static String dateStr = sdf.format(new Date());

    public static Map weeks = new HashMap<String,String>();

    static{
        weeks.put("1","Mon");
        weeks.put("2","Tue");
        weeks.put("3","Wed");
        weeks.put("4","Thu");
        weeks.put("5","Fri");
        weeks.put("6","Sat");
        weeks.put("7","Sun");
    }

    private String ymd ;//yyyyMMdd"

    private String desc;

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
        try {
        Date date =  sdf.parse(ymd);

            if(ymd.equalsIgnoreCase(dateStr)){
                setDesc("Today");
            }else {
               String month = String.format(l,"%tb", date);
                String day = String.format("%td", date);
                String week = weeks.get(Utils.dayForWeek(ymd)+"").toString();
                setDesc(week+" "+month+" "+day);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
