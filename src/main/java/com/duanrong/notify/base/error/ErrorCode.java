package com.duanrong.notify.base.error;

/**
 * 错误码
 *
 * @author xiao
 * 错误码枚举定义 统一大写字母，单词之间"_"下划线隔开
 */
public enum ErrorCode {
    /**
     * 0	请求处理失败
     * 1	请求处理成功
     */

    SUCCESS("1", "发送成功"),
    FAIL("0", "发送失败"),

    /**
     * 30 基本信息异常
     */
    RequestTooMany("30000", "您请求的次数过多，暂时被屏蔽，请联系客服人员。"),
    ParametersError("30001", "传入的参数错误"),

    /**
     * 40 鉴权错误
     */
    REFUSE("40006", "请求被拒"),
    SIGN_INVALID("40061", "签名无效"),
    TIMESTAMP_INVALID("40062", "时间戳无效"),
    TIMESTAMP_EXPIRE("40063", "时间戳过期"),
    VERSION_INVALID("40064", "接口版本号无效"),

    /**
     * 41 用户异常
     */
    UserNoRegist("41000", "用户未注册"),
    UserDisable("41001", "用户被禁用"),


    ServerFail("50001", "系统错误");

    private final String errorCode;
    private final String errorMessage;

    private ErrorCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String toString() {
        return "[" + "\"errorCode\":\"" + this.errorCode + "\", "
                + "\", \"errorMessage\":\"" + this.errorMessage + "\"]";
    }
}
