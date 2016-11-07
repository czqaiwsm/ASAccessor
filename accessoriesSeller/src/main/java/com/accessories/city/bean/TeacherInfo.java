package com.accessories.city.bean;

import java.io.Serializable;

/**
 * @desc 老师的简介
 * @creator caozhiqing
 * @data 2016/3/18
 */
public class TeacherInfo implements Serializable {

    private String id;
    private String nickName;
    private String headImg;
    private String isIdCardVip;//是否身份认证	是	Int	0-不是，1-是
    private String isSchoolVip;//是否学历认证	是	Int	0-不是，1-是
    private String price;

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getIsIdCardVip() {
        return isIdCardVip;
    }

    public void setIsIdCardVip(String isIdCardVip) {
        this.isIdCardVip = isIdCardVip;
    }

    public String getIsSchoolVip() {
        return isSchoolVip;
    }

    public void setIsSchoolVip(String isSchoolVip) {
        this.isSchoolVip = isSchoolVip;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
