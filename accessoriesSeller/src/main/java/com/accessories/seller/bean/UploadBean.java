package com.accessories.seller.bean;

/**
 * @desc 图片上传
 * @creator caozhiqing
 * @data 2016/4/6
 */
public class UploadBean {


    private String message;
    private String result;
    private String filePath;
    private String absFilePath;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAbsFilePath() {
        return absFilePath;
    }

    public void setAbsFilePath(String absFilePath) {
        this.absFilePath = absFilePath;
    }
}
