package com.accessories.city.bean;

/**
 * @author czq
 * @desc 课程设置请求字段
 * @date 16/4/6
 */
public class QueryStudentInfo {

    private String studentId	;//学生id
    private String studentName	;//学生姓名
    private String courseName	;//课程名称
    private String courseId	    ;// 课程id
    private String grade	    ;//年级id
    private String gradeName	;//年级名称

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        grade = grade;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}
