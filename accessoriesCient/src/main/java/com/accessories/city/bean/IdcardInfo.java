package com.accessories.city.bean;

import java.io.Serializable;

/**
 * @author czq
 * @desc 身份认证信息
 * @date 16/5/21
 */
public class IdcardInfo implements Serializable{

    private String realName	;//真实姓名
    private String idcard	;//身份证号
    private String idcardImg;//	身份证图片


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcardImg() {
        return idcardImg;
    }

    public void setIdcardImg(String idcardImg) {
        this.idcardImg = idcardImg;
    }
}
