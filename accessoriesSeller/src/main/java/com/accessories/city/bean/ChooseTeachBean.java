package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @author czq
 * @desc 选择老师
 * @date 16/3/31
 */
public class ChooseTeachBean extends PageInfo {


    private ArrayList<TeacherInfo> elements;


    public ArrayList<TeacherInfo> getElements() {
        return elements;
    }

    public void setElements(ArrayList<TeacherInfo> elements) {
        this.elements = elements;
    }


}
