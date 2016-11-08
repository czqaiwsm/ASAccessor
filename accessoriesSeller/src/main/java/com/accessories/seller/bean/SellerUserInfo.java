package com.accessories.seller.bean;

import java.io.Serializable;

/**
 * Created by czq on 16/11/8.
 */
public class SellerUserInfo implements Serializable{

            private String shopPic     ;
            private String vip         ;
            private String cityId      ;
            private String shopName    ;
            private String shopId      ;
            private String vipStartDate;
            private String cityName    ;
            private String vipEndDate  ;

    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getVipStartDate() {
        return vipStartDate;
    }

    public void setVipStartDate(String vipStartDate) {
        this.vipStartDate = vipStartDate;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getVipEndDate() {
        return vipEndDate;
    }

    public void setVipEndDate(String vipEndDate) {
        this.vipEndDate = vipEndDate;
    }
}
