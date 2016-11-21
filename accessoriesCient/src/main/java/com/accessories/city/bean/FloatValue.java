package com.accessories.city.bean;

/**
 * Created by czq on 16/11/4.
 */
public class FloatValue {

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    private Float value;

    private Integer msgIntegral;//每次消耗积分 "60",
    private Integer userIntegral;// 5000,
    private Integer msgSendMinIntegral;//最小积分 "10"


    public Integer getMsgIntegral() {
        return msgIntegral;
    }

    public void setMsgIntegral(Integer msgIntegral) {
        this.msgIntegral = msgIntegral;
    }

    public Integer getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(Integer userIntegral) {
        this.userIntegral = userIntegral;
    }

    public Integer getMsgSendMinIntegral() {
        return msgSendMinIntegral;
    }

    public void setMsgSendMinIntegral(Integer msgSendMinIntegral) {
        this.msgSendMinIntegral = msgSendMinIntegral;
    }

}
