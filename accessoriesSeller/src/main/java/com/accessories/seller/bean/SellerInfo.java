package com.accessories.seller.bean;

import java.util.ArrayList;

/**
 * @desc 请用一句话描述此文件
 * @creator caozhiqing
 * @data 2016/6/23
 */
public class SellerInfo {

    private String id;//ece6d74f50ea4c6f965170cbe9105e48", //商家ID
    private String tel2;//222222222", //电话2
    private String shopAddr;//测试商家1测试商家1测试商家1", //商家地址
    private String wx;//651339646", //微信
    private String phone;//18019585305", //手机
    private String shopName;//测试商家2", //商家名称
    private String tel;//"111111111111", //电话
    private String shopDesc;//"测试商家1测试商家1测试商家1测试商家1测试商家1测试商家1测试商家1测试商家1测试商家1测试商家1测试商家1测试商家1",
    private String phone2;//"18019585306", //手机2
    private String qq;//651339644"//QQ
    private String shopPic;//----------"//图片
    private String account;//
    private String phoneCalledNum;//
    private String vip;//
    private ArrayList<Phone> phoneAry;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhoneCalledNum() {
        return phoneCalledNum;
    }

    public void setPhoneCalledNum(String phoneCalledNum) {
        this.phoneCalledNum = phoneCalledNum;
    }

    public ArrayList<Phone> getPhoneAry() {
        return phoneAry;
    }

    public void setPhoneAry(ArrayList<Phone> phoneAry) {
        this.phoneAry = phoneAry;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }
}
