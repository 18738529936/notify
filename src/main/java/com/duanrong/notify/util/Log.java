package com.duanrong.notify.util;

import com.duanrong.util.PropReader;
import com.duanrong.util.client.DRHTTPClient;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 日志记录工具类
 * @Author: 林志明
 * @CreateDate: Aug 28, 2014
 */
@Component
public class Log {

    private static String hostName; //网站名

    private static String server; //本机IP

    private static String sign = "B688513358A7DA6A97B4069EC1D062EC"; //签名

    // 文本格式
    //private static String CONTENT_TYPE = "text/plain;charset=UTF-8";

    private static String serverUrl;  //错误日志请求地址

    private static String serverUrl2; //信息日志请求地址

    static {
        PropReader reader = new PropReader("/config.properties");
        hostName = reader.readProperty("hostName"); //获取网站地址
        String url = reader.readProperty("logServerUrl"); //获取网站地址
        serverUrl = url + "/log/error";
        serverUrl2 = url + "/log/info";
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            server = addr.getHostAddress();//获得本机IP
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送http请求
     *
     * @param serverUrl
     * @param title
     * @param content
     */
    private void sendHTTPRequest(String serverUrl, String title, String content) {
        sendHTTPRequest(serverUrl, title, content, 0, null);
    }

    /**
     * 发送http请求
     *
     * @param serverUrl
     * @param title
     * @param content
     * @param level     错误等级
     */
    private void sendHTTPRequest(String serverUrl, String title,
                                 String content, int level) {
        sendHTTPRequest(serverUrl, title, content, level, null);
    }

    /**
     * 发送http请求
     *
     * @param serverUrl
     * @param title
     * @param content
     * @param level
     * @param email     发送邮件
     */
    private void sendHTTPRequest(String serverUrl, String title,
                                 String content, int level, String email) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("title", title));
        params.add(new BasicNameValuePair("content", content));
        params.add(new BasicNameValuePair("domain", hostName));
        params.add(new BasicNameValuePair("host", server));
        params.add(new BasicNameValuePair("level", "" + level));
        params.add(new BasicNameValuePair("mail", email));
        try {
            DRHTTPClient.sendHTTPRequestPostToString(serverUrl, new Header[]{new BasicHeader("sign", sign)}, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录错误日志
     *
     * @param title
     * @param content
     */
    public void errLog(String title, String content) {
        sendHTTPRequest(serverUrl, title, content);
    }

    /**
     * 记录错误日志
     *
     * @param title
     * @param content
     * @param level
     */
    public void errLog(String title, String content, int level) {
        sendHTTPRequest(serverUrl, title, content, level);
    }

    /**
     * @param title
     * @param content
     * @param level
     * @param email   发送邮件
     */
    public void errLog(String title, String content, int level, String email) {
        sendHTTPRequest(serverUrl, title, content, level, email);
    }

    /**
     * 记录错误日志
     *
     * @param title   标题
     * @param content 内容
     */
    public void errLog(String title, Throwable ex) {
        sendHTTPRequest(serverUrl, title, ExceptionUtils.getStackTrace(ex));
    }

    /**
     * 记录错误日志
     *
     * @param title   标题
     * @param content 内容
     */
    public void errLog(String title, Throwable ex, int level) {
        sendHTTPRequest(serverUrl, title, ExceptionUtils.getStackTrace(ex), level);
    }

    /**
     * 记录信息日志
     *
     * @param title   标题
     * @param content 内容
     */
    public void infoLog(String title, String content) {
        sendHTTPRequest(serverUrl2, title, content);
    }

    /**
     * 记录信息日志
     *
     * @param title   标题
     * @param content 内容
     */
    public void infoLog(String title, String content, int level) {
        sendHTTPRequest(serverUrl2, title, content, level);
    }

    /**
     * @param title
     * @param content
     * @param level
     * @param email
     */
    public void infoLog(String title, String content, int level, String email) {
        sendHTTPRequest(serverUrl2, title, content, level, email);
    }

    /**
     * 记录信息日志
     *
     * @param title   标题
     * @param content 内容
     */
    public void infoLog(String title, Throwable ex) {
        sendHTTPRequest(serverUrl2, title, ExceptionUtils.getStackTrace(ex));
    }

    /**
     * 记录信息日志
     *
     * @param title   标题
     * @param content 内容
     */
    public void infoLog(String title, Throwable ex, int level) {
        sendHTTPRequest(serverUrl2, title, ExceptionUtils.getStackTrace(ex),
                level);
    }

    public void infoLog(String title, Throwable ex, int level, String email) {
        sendHTTPRequest(serverUrl2, title, ExceptionUtils.getStackTrace(ex),
                level, email);
    }
}
