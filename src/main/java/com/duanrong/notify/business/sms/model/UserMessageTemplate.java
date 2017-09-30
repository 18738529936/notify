package com.duanrong.notify.business.sms.model;

import com.duanrong.notify.base.model.BaseModel;

import java.util.Date;

public class UserMessageTemplate extends BaseModel {

    private String id;
    private String messageNode;
    private String messageWay;
    private String template;
    private String description;
    private String name;
    private String status;
    private String templateView;
    private Date lastModifyTime;
    private String textuserId;
    private int nowStatus;
    private String channel;
    private String infoTemplate;//站内信模板


    /**
     * @return the infoTemplate
     */
    public String getInfoTemplate() {
        return infoTemplate;
    }

    /**
     * @param infoTemplate the infoTemplate to set
     */
    public void setInfoTemplate(String infoTemplate) {
        this.infoTemplate = infoTemplate;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the templateView
     */
    public String getTemplateView() {
        return templateView;
    }

    /**
     * @param templateView the templateView to set
     */
    public void setTemplateView(String templateView) {
        this.templateView = templateView;
    }

    /**
     * @return the lastModifyTime
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * @param lastModifyTime the lastModifyTime to set
     */
    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * @return the textuserId
     */
    public String getTextuserId() {
        return textuserId;
    }

    /**
     * @param textuserId the textuserId to set
     */
    public void setTextuserId(String textuserId) {
        this.textuserId = textuserId;
    }

    /**
     * @return the nowStatus
     */
    public int getNowStatus() {
        return nowStatus;
    }

    /**
     * @param nowStatus the nowStatus to set
     */
    public void setNowStatus(int nowStatus) {
        this.nowStatus = nowStatus;
    }

    /**
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageNode() {
        return messageNode;
    }

    public void setMessageNode(String messageNode) {
        this.messageNode = messageNode;
    }

    public String getMessageWay() {
        return messageWay;
    }

    public void setMessageWay(String messageWay) {
        this.messageWay = messageWay;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UserMessageTemplate [id=" + id + ", messageNode=" + messageNode
                + ", messageWay=" + messageWay + ", template=" + template
                + ", description=" + description + ", name=" + name
                + ", status=" + status + ", templateView=" + templateView
                + ", lastModifyTime=" + lastModifyTime + ", textuserId="
                + textuserId + ", nowStatus=" + nowStatus + ", channel="
                + channel + "]";
    }

    private static final long serialVersionUID = 1946442400173160437L;

}