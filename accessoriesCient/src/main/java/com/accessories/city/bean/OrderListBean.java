package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @author czq
 * @desc 选择老师
 * @date 16/3/31
 */
public class OrderListBean extends PageInfo {


    private ArrayList<OrderInfo> elements;


    public ArrayList<OrderInfo> getElements() {
        return elements;
    }

    public void setElements(ArrayList<OrderInfo> elements) {
        this.elements = elements;
    }


}
