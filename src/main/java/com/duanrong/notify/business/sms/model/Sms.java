package com.duanrong.notify.business.sms.model;

import com.duanrong.notify.base.model.BaseModel;

import java.util.Date;

public class Sms extends BaseModel {

    private static final long serialVersionUID = 4028643806162628481L;

    private String id;
    private String userId;
    private String mobileNumber;
    private String content;
    private Date time;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Sms [id=" + id + ", userId=" + userId + ", mobileNumber="
                + mobileNumber + ", content=" + content + ", time=" + time
                + ", type=" + type + "]";
    }

}