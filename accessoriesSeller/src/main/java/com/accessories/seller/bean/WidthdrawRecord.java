package com.accessories.seller.bean;

/**
 * Created by czq on 16/10/30.
 */
public class WidthdrawRecord {

    private String time;//时间
    private String cashIntegral;//提现积分
    private String cashType;//提现类型 0支付宝 1微信
    private String cashMoney;//提现金额
    private String cashStatus;////提现状态 0处理中 1已完成 2拒绝
    private String cashDesc;//提现描述 拒绝会有拒绝理由


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCashIntegral() {
        return cashIntegral;
    }

    public void setCashIntegral(String cashIntegral) {
        this.cashIntegral = cashIntegral;
    }

    public String getCashType() {
        return cashType;
    }

    public void setCashType(String cashType) {
        this.cashType = cashType;
    }

    public String getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(String cashMoney) {
        this.cashMoney = cashMoney;
    }

    public String getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(String cashStatus) {
        this.cashStatus = cashStatus;
    }

    public String getCashDesc() {
        return cashDesc;
    }

    public void setCashDesc(String cashDesc) {
        this.cashDesc = cashDesc;
    }
}
