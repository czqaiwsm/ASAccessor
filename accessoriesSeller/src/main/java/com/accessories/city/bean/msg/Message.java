package com.accessories.city.bean.msg;

import java.io.Serializable;

/**
 * 消息信息
 * @desc 请用一句话描述它
 * @date 16/3/17
 */
public class Message implements Serializable {


    private String headPhoto; //头像地址
    private String time;
    private String msg_content;
    private String name;


    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {

        this.msg_content = msg_content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
