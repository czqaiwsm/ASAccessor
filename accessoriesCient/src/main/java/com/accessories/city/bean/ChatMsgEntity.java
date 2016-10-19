
package com.accessories.city.bean;

import java.io.Serializable;

public class ChatMsgEntity implements Serializable{
    private String senderId	;//发 送者ID
    private String receiverId ;//	接受者ID
    private String content	;//消息内容
    private String studentImg ;//	学生头像
    private String teacherImg ;//	老师头像
    private String createTime ;//	创建时间
    private String direction ;//	聊天方向	是	Int	聊天方向：1-左(对方)，2-右(自己)
    private String studentName	;//老师姓名


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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

    public String getStudentImg() {
        return studentImg;
    }

    public void setStudentImg(String studentImg) {
        this.studentImg = studentImg;
    }

    public String getTeacherImg() {
        return teacherImg;
    }

    public void setTeacherImg(String teacherImg) {
        this.teacherImg = teacherImg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
