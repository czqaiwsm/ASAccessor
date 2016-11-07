package com.accessories.city.bean;

/**
 * @author czq
 * @desc 消息列表
 * @date 16/4/12
 */
public class MsgDetail {
    private String id;//消息ID
    private String senderId;//当前登录者id
    private String receiverId;//消息接受者id
    private String content;//消息内容
    private String createTime;//创建时间
    private String headImg;//用户头像
    private String studentImg;

    private String derection;//": 1,
    private String studentName;//": "测试",

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getStudentImg() {
        return studentImg;
    }

    public void setStudentImg(String studentImg) {
        this.studentImg = studentImg;
    }

    public String getDerection() {
        return derection;
    }

    public void setDerection(String derection) {
        this.derection = derection;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
