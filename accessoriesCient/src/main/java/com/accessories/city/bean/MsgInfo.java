package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @author czq
 * @desc 消息列表
 * @date 16/4/12
 */
public class MsgInfo {

    private String content;
    private String time;
    private String phone;
    private String address;
    private String nickname;
    private String userHead;
    private ArrayList<String > picAry;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<String> getPicAry() {
        return picAry;
    }

    public void setPicAry(ArrayList<String> picAry) {
        this.picAry = picAry;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
}
