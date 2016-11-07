package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/6/22
 */
public class CityList {

    private ArrayList<City> cityList;
    private String proName;

    public ArrayList<City> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }
}
