package com.accessories.city.bean;

/**
 * @desc 首页的banner图
 * @creator caozhiqing
 * @data 2016/3/30
 */
public class HomePagerBanner {
    private String id;//广告唯一标识ID
    private String title;//广告标题
    private String picUrl;//广告显示的图片地址
    private String redirect;//客户端页面跳转url

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
