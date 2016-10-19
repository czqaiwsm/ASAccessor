package com.accessories.city.bean;

/**
 * @desc banner或广告的信息
 * @creator caozhiqing
 * @data 2015/11/24
 */
public class BannerImgInfo {
    private String id;//广告唯一标识ID
    private String title;//广告标题
    private String redirect;//客户端页面跳转url
    private String url = "";

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


    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
