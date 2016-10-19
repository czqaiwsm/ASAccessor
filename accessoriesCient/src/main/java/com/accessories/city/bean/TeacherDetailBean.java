package com.accessories.city.bean;

import java.util.ArrayList;

/**
 * @author czq
 * @desc 老师详情
 * @date 16/4/7
 */
public class TeacherDetailBean {

    private ArrayList<CourseInfo> courseList;

    private TeacherDetailInfo teacherInfo ;

    public ArrayList<CourseInfo> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<CourseInfo> courseList) {
        this.courseList = courseList;
    }

    public TeacherDetailInfo getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherDetailInfo teacherInfo) {
        this.teacherInfo = teacherInfo;
    }
}
