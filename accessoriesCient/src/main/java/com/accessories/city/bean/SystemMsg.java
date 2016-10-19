package com.accessories.city.bean;

/**
 * @author czq
 * @desc 系统消息
 * @date 16/4/7
 */
public class SystemMsg {
    private String id;
    private String title;//	String
    private String content;//是	String
    private String createTime;//建时间	是	Date	yyyy-MM-dd HH:MM:ss

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
