package com.accessories.city.bean;

/**
 * @author czq
 * @desc 版本
 * @date 16/5/10
 */
public class Version {

    private String content;//部分BUG提交",//更新内容
    private String appUrl;//,//下载地址
    private String versionNo;//2",//版本号
    private String versionName;//v1.1"//版本名称

    private String isForce = "0";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }


    public static void main(String args[]){

        System.out.println("12".compareTo("11"));
    }

}
