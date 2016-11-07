package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @author czq
 * @desc 选择老师
 * @date 16/3/31
 */
public class CateListBean extends PageInfo {


    private ArrayList<CateSubTypeEntity> array;


    public ArrayList<CateSubTypeEntity> getArray() {
        return array;
    }

    public void setArray(ArrayList<CateSubTypeEntity> array) {
        this.array = array;
    }
}
