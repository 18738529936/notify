package com.duanrong.notify.util;

import com.duanrong.util.client.DRHTTPClient;
import com.duanrong.util.security.Hmac;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmsHttpClient {

    /***SMS***/
    private static final String smsUrl = "https://soa-notify.duanrong.com/";  //sms域名
    /*private static final String smsUrl = "http://localhost:8080/";*/
    private static final String smskey = "duanrongf0f22ac60d07407cfb7c587f9cab"; //smsKey
    private static final String smsSource = "soa"; //来源
    private static final String smsVersion = "1.0.0";

    private static String ip = "";   //当前服务器IP地址

    static {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "无法获得IP";
        }
    }


    private void send(String service, Object obj) {
        List<NameValuePair> params = new ArrayList<>();
        Long timestamp = System.currentTimeMillis();
        //数据加密
        try {
            String data = new String(Base64.encode(FastJsonUtil.objToJson(obj).getBytes("utf-8")));
            String sign = new String(Base64.encode(Hmac.hmacSHA256(
                    (timestamp + "|" + smsSource + "|" + smsVersion + "|" + data).getBytes("utf-8"), smskey.getBytes("utf-8"))), "utf-8");
            params.add(new BasicNameValuePair("timestamp", "" + timestamp));
            params.add(new BasicNameValuePair("source", smsSource));
            params.add(new BasicNameValuePair("version", smsVersion));
            params.add(new BasicNameValuePair("ip", ip));
            params.add(new BasicNameValuePair("data", data));
            params.add(new BasicNameValuePair("sign", sign));
            DRHTTPClient.sendHTTPRequestPostToString(DRHTTPClient.createSSLClientDefault(), smsUrl + service, new Header[0], params);
        } catch (InvalidKeyException | IOException e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 调用发送邮件接口
     *
     * @param subject
     * @param content
     * @param mailtos
     * @return
     */
    public void sendEmail(String subject, String content, String mailtos) {
        String url = "/sms/sendEmail.do";
        Map<String, Object> param = new HashMap<>();
        param.put("subject", subject);
        param.put("content", content);
        param.put("mailtos", mailtos);
        this.send(url, param);
    }

    private void send(String url, String mobiles, String params, String content, String type, String channel) {
        Map<String, Object> param = new HashMap<>();
        param.put("mobiles", mobiles);
        param.put("params", params);
        param.put("content", content);
        param.put("type", type);
        param.put("channel", channel);
        this.send(url, param);
    }

    private void sendInfo(String url, String userId, String title, String content) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("title", title);
        param.put("content", content);
        this.send(url, param);
    }

    /**
     * 发送模板短信和站内信
     *
     * @param mobiles
     * @param params
     * @param type
     * @return
     */
    public void sendSms(String mobiles, String params, String type) {
        String url = "/sms/sendSmsTemplate.do";
        this.send(url, mobiles, params, null, type, null);

    }

    /**
     * 发送自定义短信
     *
     * @param mobiles
     * @param content
     * @param type
     * @param channel
     * @return
     */
    public void sendSms(String mobiles, String content, String type, String channel) {
        String url = "/sms/sendSmsCustomize.do";
        this.send(url, mobiles, null, content, type, channel);

    }

    /**
     * 发送自定义站内信
     *
     * @param mobiles
     * @param content
     * @param type
     * @param channel
     * @return
     */
    public void sendInformation(String userId, String title, String content) {
        String url = "/sms/sendInformationCustomize.do";
        this.sendInfo(url, userId, title, content);

    }
}
