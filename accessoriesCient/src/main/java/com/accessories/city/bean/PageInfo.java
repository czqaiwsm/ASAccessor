package com.accessories.city.bean;

/**
 * @author czq
 * @desc 下拉刷新的公共字段
 * @date 16/4/6
 */
public class PageInfo {

    private int pageNo;//当前页数
    private int pageSize;//每页大小
    private int totalCount;//总记录数
    private int totalPages;//总页数


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


}
