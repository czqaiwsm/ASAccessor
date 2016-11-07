package com.accessories.city.bean;

/**
 * @desc 登录信息
 * @creator caozhiqing
 * @data 2016/3/30
 */
public class LoginInfo {

    private String token;

    private UserInfo userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
