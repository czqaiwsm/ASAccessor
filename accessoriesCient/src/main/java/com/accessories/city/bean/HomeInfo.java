package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @desc 主页
 * @creator caozhiqing
 * @data 2016/3/30
 */
public class HomeInfo {
    private ArrayList<HomePagerBanner> bannerAry;
    private ArrayList<CateSubTypeEntity> catSingleAry;//单项件分类
    private ArrayList<CateSubTypeEntity> catAllAry;//全车件分类

    public ArrayList<HomePagerBanner> getBannerAry() {
        return bannerAry;
    }

    public void setBannerAry(ArrayList<HomePagerBanner> bannerAry) {
        this.bannerAry = bannerAry;
    }

    public ArrayList<CateSubTypeEntity> getCatSingleAry() {
        return catSingleAry;
    }

    public void setCatSingleAry(ArrayList<CateSubTypeEntity> catSingleAry) {
        this.catSingleAry = catSingleAry;
    }

    public ArrayList<CateSubTypeEntity> getCatAllAry() {
        return catAllAry;
    }

    public void setCatAllAry(ArrayList<CateSubTypeEntity> catAllAry) {
        this.catAllAry = catAllAry;
    }
}
