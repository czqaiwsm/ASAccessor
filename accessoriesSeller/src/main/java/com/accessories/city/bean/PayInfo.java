package com.accessories.city.bean;

/**
 * @author czq
 * @desc 支付订单
 * @date 16/4/22
 */
public class PayInfo {

    private String orderNum;
    private String price;
    private String productDesc;
    private String content;
    private String notifyUrl;

    public PayInfo(String orderNum, String price, String productDesc, String content){
        this.orderNum = orderNum;
        this.price = price;
        this.productDesc = productDesc;
        this.content = content;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
