package com.accessories.city.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/3/15
 */
public class PhoneUitl {


    // 判断是否为手机号码
    public static boolean isPhone(String strPhone) {
        String str = "^1[3|4|5|7|8][0-9]\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(strPhone);
        return m.matches();
    }
}
