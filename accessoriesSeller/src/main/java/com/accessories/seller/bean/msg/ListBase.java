package com.accessories.seller.bean.msg;

import java.util.ArrayList;

/**
 * Created by czq on 16/10/29.
 */
public class ListBase<T> {

    private Integer page;
    private Integer pageSize;
    private Integer totalPage;
    private ArrayList<T> array;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<T> getArray() {
        return array;
    }

    public void setArray(ArrayList<T> array) {
        this.array = array;
    }
}
