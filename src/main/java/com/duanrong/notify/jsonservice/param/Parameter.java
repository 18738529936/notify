package com.duanrong.notify.jsonservice.param;

import java.io.Serializable;

/**
 * 请求参数父类, 接口输入参数由此类定义, 根据具体业务类型和接口自由扩展, 扩展参数慎重，不要扩展无意义字段
 * 接口绑定参数 @RequestParmter 需要继承此类，否则抛出接口参数绑定异常
 *
 * @author xiao
 * @datetime 2016/10/20 17:10:12
 */
public class Parameter implements Serializable {

    private static final long serialVersionUID = -2510079586352986286L;

    // 用户id
    private String userId;

    // 用户来源
    private String userSource;

    // 流水号
    private String requestNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    @Override
    public String toString() {
        return "Paramter [userId=" + userId + ", userSource=" + userSource
                + ", requestNo=" + requestNo + "]";
    }

}
