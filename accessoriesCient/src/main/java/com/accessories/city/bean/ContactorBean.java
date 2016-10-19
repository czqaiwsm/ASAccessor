package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/4/9
 */
public class ContactorBean extends PageInfo {

    private ArrayList<Contactor> elements;

    public ArrayList<Contactor> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Contactor> elements) {
        this.elements = elements;
    }
}
