package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @author czq
 * @desc 评价列表
 * @date 16/4/6
 */
public class CommentBean  extends PageInfo  {


    private ArrayList<CommentInfo> elements;

    public ArrayList<CommentInfo> getElements() {
        return elements;
    }

    public void setElements(ArrayList<CommentInfo> elements) {
        this.elements = elements;
    }
}
