package com.accessories.city.bean;

/**
 * @author czq
 * @desc 请用一句话描述它
 * @date 16/4/20
 */
public class PayCourseInfo {
    private String orderCode;//订单编号	是
    private String payPrice	   ;//支付金额	是
    private String courseName;//课程名称	是
    private String teacherName;//老师姓名	是
    private String createTime;//下单日期	是

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
