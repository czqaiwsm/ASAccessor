package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @desc 请用一句话描述此文件
 * @creator caozhiqing
 * @data 2016/6/23
 */
public class SellerList {

        private String page     ;//1, //当前页数
        private String pageSize ;// 20, //每页数据量
        private String totalPage;//1, //总页数
        private ArrayList<NewsEntity> array    ;//

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<NewsEntity> getArray() {
        return array;
    }

    public void setArray(ArrayList<NewsEntity> array) {
        this.array = array;
    }
}
