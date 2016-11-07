package com.accessories.city.bean;

/**
 * @author czq
 * @desc 学生对老师的评价
 * @date 16/4/6
 */
public class CommentInfo {

    private String studentName;//	姓名	是	String	学生姓名
    private String headImg	;//用户头像	是	String	学生头像
    private String grade;//	年级	是	String	学生所在的年级
    private String serviceScore;//	服务评星	是	Int	服务分数1-5分
    private String commentDesc;//	评价	是	String	学生评价
    private String commentTime	;//评价时间	是	Date	格式：yyyy-MM-dd

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(String serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getCommentDesc() {
        return commentDesc;
    }

    public void setCommentDesc(String commentDesc) {
        this.commentDesc = commentDesc;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
