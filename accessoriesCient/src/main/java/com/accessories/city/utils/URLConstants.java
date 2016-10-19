package com.accessories.city.utils;

//import com.alipay.sdk.pay.demo.AlipayConstant;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/3/15
 */
public class URLConstants {
//
//    https://svn.duapp.com/appid7ofdiffbr0/learn
//    No1RightAnswer
//    ls/2016

    public static int SCREENH = 0;
    public static int SCREENW = 0;
    public static final String SUCCESS_CODE = "0";
    /********requestCode*************/
    public static final int CHOOSE_CITY_REQUEST_CODE = 11;//城市选择
    public static final int CHOOSE_JOINOR_REQUEST_CODE = 22;//年级选择
    public static final int CHOOSE_DEGREE_REQUEST_CODE = 33;//学历选择
    public static final int CHOOSE_SUBJECT_REQUEST_CODE = 44;//科目选择
    /********requestCode*************/

    public static String CHOOSE = "CHOOSE";

    /********Intent value*************/
    public static final  String COURSEID = "courseId";
    public static final int ORDERLEFT = 0X20;
    public static final int ORDERRIGHT = 0X21;
    /********Intent value*************/

//    120.76.47.196:8080
//    public static final String BASE_URL = "http://120.25.171.4/learn-interface/interface/api.action";//基础URL
//    public static final String BASE_URL = "http://www.leishangnet.com/learn-teach/teach/api.action";//基础 teacher URL
    public static final String BASE_URL = "http://120.76.47.196:8080/learn-teach/teach/api.action";//基础 teacher URL

    public static final String TEACHER_UPLOAD = "http://120.76.47.196:8080/learn-teach/teach/upload.action";

    public static final String ACBASE_URL = "http://121.41.75.147:8081/carfashion/json";//车时尚 基础URL
    public static final String CATEGORYLIST =ACBASE_URL+"/categoryList.do";//类型
    public static final String Home =ACBASE_URL+"/index.do";//首页
    public static final String SHOPLIST =ACBASE_URL+"/shopList.do";//商家列表
    public static final String CITYLIST =ACBASE_URL+"/cityList.do";//城市列表
    public static final String GETMSGCODE =ACBASE_URL+"/getMsgCode.do";//获取验证码
    public static final String SHOPDETAIL =ACBASE_URL+"/shopDetail.do";//商家主页
    public static final String SHOPLOGIN =ACBASE_URL+"/shopLogin.do";//商家登录
    public static final String SHOPCALLRECORDLIST =ACBASE_URL+"/shopCallRecordList.do";//商家登录
    public static final String CALL =ACBASE_URL+"/call.do";//拨打电话 积分
    public static final String LOGIN =ACBASE_URL+"/login.do";//登录
    public static final String REGIST =ACBASE_URL+"/regist.do";//注册
    public static final String UPDATEPWD =ACBASE_URL+"/updatePwd.do";//忘记密码
    public static final String GETUSERINFO =ACBASE_URL+"/getUserInfo.do";//获取个人信息
    public static final String MSGCODE =ACBASE_URL+"/getMsgCode.do";//获取个人信息
    public static final String GETVERSION =ACBASE_URL+"/getVersion.do";//获取个人信息



//    static {
//        AlipayConstant.NOTIFY_URL = "http://120.25.171.4/learn-interface/alipayOrderServlet";
//        AlipayConstant.SELLER = "1138088601@qq.com";
//        AlipayConstant.RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALLE1FNSf0l4IF1c\n" +
//                "cBAsu4cKvp4mIzLTsuHtJSHLHwV3fpH1ReYIoNCCqMKx7CWMnFuQEeq2xlop9LHf\n" +
//                "4VSrgDN2jYMiqSHTxBHtvh7EyNLK6MRZA4CED5bHpdpVKbXbVSJboAfeNFoL/ao4\n" +
//                "5I8Uf5taaw93SyDvXzmmixh7tEkTAgMBAAECgYBVmumuCMhJT0v4SSakqDcYwK1t\n" +
//                "IAgk87kqdRwd5Z2ySXmDvXc+Y41Wq+rSaM8TYBUTx5a81c+AJ600bGgD2qhlrs3T\n" +
//                "cSVDz/lngoANxEKnCZDp1LVOOIw6KIsBy+6p/nQ9RheVpRZpHo7OiNXn+gpVZyCw\n" +
//                "fsjiwYe98FKHh+fUCQJBAOlAZQl/HUMjWFY5XxG+9rfnySXW8KsI2jMQOiCCXsYV\n" +
//                "eKJdExRyDqUkiWM5WGxj7L6QcruEnXP17ZAsJAABwfUCQQDENCmVf/SEgzMnCH6z\n" +
//                "F5otjEvWdRB9QupR9mzt82QgIwI4JjQ0nFpzwwFE1SjnZ/chpiGgyb1OP6BA8rec\n" +
//                "KRHnAkEA0v1O5+I2BA8qzwQifRjyb7SY/UOKfAwL5HjfO5zyQdgWQThUFACAQt3j\n" +
//                "8P9kftd8xXxAbGMvUj+5XtquzdrJKQJBAJ88iodX+tZVOP2z6khlnm7bD221QrW2\n" +
//                "yj/NFOkmARwH7bQuZW5ReyO6n1wC+BifCzZXA7HgKXDJOMGde6EUv/8CQDrM0ZAK\n" +
//                "wFA5DiiQh9LNu7ztSZFE+4dulNXf62aQ/qybxmYsfqM+onwL3pFt34QAuKWmXh5T\n" +
//                "0XR2JECVUVlEU9k=";
//        AlipayConstant.PARTNER = "2088221254922632";
//    }

}
