package com.duanrong.notify.util;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.util.client.DRHTTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.bouncycastle.asn1.ua.DSTU4145NamedCurves.params;

/**
 * 短信发送
 * <p>
 * Created by xiao on 2015/12/2.
 *
 * @version 1.1  增加营销短信接口
 */
public class SMSSend {

    //创蓝
    static final String httpBatchSendSM = "http://sms.253.com/msg/send";// 批量发送
    static final String account = "N3833682";// 注册号
    static final String account2 = "M9585425";// 营销短信

    static final String pswd2 = "or6RLXfJDe1b28";
    static final String pswd = "xDhzrOnGs47e78";// 密码
    /*    static final String httpBatchSendSM = "http://222.73.117.156/msg/HttpBatchSendSM";// 批量发送
        static final String account = "duanrongw";// 注册号
        static final String account2 = "vip_drw";// 营销短信

        static final String pswd2 = "Duanrongw123";
        static final String pswd = "Tch123456";// 密码
    */
    static final String httpEmay = "http://sdk999in.eucp.b2m.cn:8080/sdkproxy/sendsms.action";// 即时发送企业短信
    static final String cdkey = "9SDK-EMY-0999-RJYUS";// 注册号
    static final String password = "103903";//密码

    static final String httpEmaySales = "http://sdktaows.eucp.b2m.cn:8080/sdkproxy/sendsms.action";// 即时发送营销短信
    static final String cdkey1 = "6SDK-EMY-6666-RJROL";// 注册号
    static final String password1 = "347880";//密码

    //畅想空间短信接口
    //static final String dreamZoneUrl="http://210.51.190.19:8080/eums/utf8/send_strong.do";
    static final String dreamZoneUrl = "http://160.19.212.218:8080/eums/utf8/send_strong.do";
    //畅想备用短信接口
    static final String dreamZoneResUrl = "http://123.125.148.35:8080/eums/utf8/send_strong.do";
    static final String account_dz = "drwyx";//营销短信接口
    static final String password_dz = "8gw0e492";

    //卓越恒信短信接口
    static final String yesxinUrl = "http://open.yesxin.cn:8080/send";
    static final int yesxinNoticeAccount = 16;
    static final String yesxinNoticeKey = "f3f216b25b11c268368d7a1bb36295c5";
    static final int yesxinMarketingAccount = 17;
    static final String yesxinMarketingKey = "bf9331233156b69edc9e69838ae384b4";

    private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 批量发送，多个手机号用","隔开
     *
     * @param mobile
     * @param msg
     * @vesion 1.0
     */
    public static void batchSend(String mobile, String msg) {
        try {
            sendSM(account, pswd, mobile, msg, true, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量发送(营销短信)，多个手机号用","隔开
     *
     * @param mobile
     * @param msg
     * @version 1.1
     */
    public static void batchSend1(String mobile, String msg) {
        try {
            sendSM(account2, pswd2, mobile, msg, true, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量发送(营销短信)，多个手机号用","隔开
     *
     * @param mobile  手机号
     * @param msg     短信内容
     * @param channel emay亿美通知短信,emay_sales亿美营销短信,chuanglan创蓝注册短信,chuanglan_sales创蓝营销短信,dreamzone_sales空间畅想营销通知短信
     */
    // TODO 短信发送
    public static void batchSends(String mobile, String msg, String channel) {
        try {
            //emay亿美只能批量发送200条短信
            if ("emay_sales".equals(channel)) {
                sendEmaySalesMsg(mobile, msg);
            } else if ("emay".equals(channel)) {
                sendEmayMsg(mobile, msg);
            } else if ("chuanglan".equals(channel)) {
                sendSM(account, pswd, mobile, msg, true, null, null);
            } else if ("chuanglan_sales".equals(channel)) {
                sendSM(account2, pswd2, mobile, msg, true, null, null);
            } else if ("dreamzone_sales".equals(channel)) {
                sendSMSByDZ(mobile, msg);
            } else if ("yesxin".equals(channel)) {
                sendSMByYesxin(yesxinUrl, yesxinNoticeAccount, yesxinNoticeKey, mobile, msg);
            } else if ("yesxin_sales".equals(channel)) {
                sendSMByYesxin(yesxinUrl, yesxinMarketingAccount, yesxinMarketingKey, mobile, msg);
            } else {
                sendSM(account, pswd, mobile, msg, true, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 即时企业短信发送，多个手机号用","隔开
     *
     * @param phone
     * @param message
     * @version 1.0
     */
    public static void sendEmayMsg(String phone, String message) {
        try {
            sendEmaySM(httpEmay, cdkey, password, phone, message, null, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量即时发送(营销短信)，多个手机号用","隔开
     *
     * @param phone
     * @param message
     * @version 1.1
     */
    public static void sendEmaySalesMsg(String phone, String message) {
        try {
            sendEmaySM(httpEmaySales, cdkey1, password1, phone, message, null, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送短信
     *
     * @param mobile
     * @param msg
     * @param needstatus
     * @param product
     * @param extno
     * @version 1.0
     */
    // TODO 创蓝短信发送
    private static void sendSM(String account, String pswd, String mobile, String msg,
                               boolean needstatus, String product, String extno) throws IOException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("un", account));
        params.add(new BasicNameValuePair("pw", pswd));
        params.add(new BasicNameValuePair("phone", mobile));
        params.add(new BasicNameValuePair("rd", needstatus ? "1" : "0"));
        msg = URLEncoder.encode(msg, "UTF-8");
        params.add(new BasicNameValuePair("msg", msg));
        params.add(new BasicNameValuePair("product", product));
        params.add(new BasicNameValuePair("ex", extno));
        String result = DRHTTPClient.sendHTTPRequestPostToString(httpBatchSendSM, new BasicHeader[0], params, "UTF-8");
        Log log = new Log();
        log.infoLog("短信发送记录，手机号：" + mobile, "响应结果：" + result + "，内容：" + URLDecoder.decode(msg, "UTF-8"));
    }

    /**
     * 卓越恒信短信发送
     * @param serviceUrl
     * @param key
     * @param account
     * @param mobile
     * @param content
     * @throws IOException
     */
    private static void sendSMByYesxin(String serviceUrl, int account, String key, String mobile, String content) throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("account", Integer.toString(account)));
        params.add(new BasicNameValuePair("mobile", mobile));
//        content = URLEncoder.encode(content, "UTF-8");
        params.add(new BasicNameValuePair("content", content));
        String str = "account=" + account + "&content=" + content + "&mobile=" + mobile + key;
        String sign = HashCrypt.getDigestHash(str, "MD5");
        params.add(new BasicNameValuePair("sign", sign));
        String result = HttpClient.sendHTTPRequestPostToString(serviceUrl, new BasicHeader[0], params, "UTF-8");

        System.out.println(result);
        Log log = new Log();
        log.infoLog("短信发送记录，手机号：" + mobile, "响应结果：" + result + "，内容：" + URLDecoder.decode(content, "UTF-8"));
    }

    /**
     * 即时发送短信
     *
     * @param phone
     * @param message
     * @param addserial
     * @param seqid
     * @param smspriority
     * @version 1.1
     */
    private static void sendEmaySM(String url, String cdkey, String password, String phone, String message,
                                   String addserial, String seqid, String smspriority) throws IOException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("cdkey", cdkey));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("message", message));
        params.add(new BasicNameValuePair("addserial", addserial));
        params.add(new BasicNameValuePair("seqid", seqid));
        params.add(new BasicNameValuePair("smspriority", smspriority));
        DRHTTPClient.sendHTTPRequestGetToString(url, new BasicHeader[0], params);
    }

    /**
     * 空间畅想营销短信发送
     *
     * @param dest    手机号码，多个联系人以逗号隔开
     * @param content 短信内容
     * @throws IOException
     */
    public static void sendSMSByDZ(String dest, String content) throws IOException {
        List<NameValuePair> params = new ArrayList<>();
        String seed = df.format(new Date());
        String key = MD5Encry.Encry(MD5Encry.Encry(password_dz).toLowerCase() + seed).toLowerCase();
        params.add(new BasicNameValuePair("name", account_dz));
        params.add(new BasicNameValuePair("seed", seed));
        params.add(new BasicNameValuePair("key", key));
        params.add(new BasicNameValuePair("dest", dest));
        //判断短信内容是否包含签名
        if (content.indexOf("【短融网】") > -1 || content.indexOf("[短融网]") > -1) {
            params.add(new BasicNameValuePair("content", content));
        } else {
            params.add(new BasicNameValuePair("content", "【短融网】" + content));
        }
        String result = DRHTTPClient.sendHTTPRequestPostToString(dreamZoneUrl, new BasicHeader[0], params, "UTF-8");
        String resultRes = "";
        if (!result.contains("success")) {
            resultRes = DRHTTPClient.sendHTTPRequestPostToString(dreamZoneResUrl, new BasicHeader[0], params, "UTF-8");
        }
        Log log = new Log();
        log.infoLog("短信发送记录，手机号：" + dest, "响应结果：" + result + "备用响应结果：" + resultRes + "，内容：" + URLDecoder.decode(content, "UTF-8"));
        System.err.println("name=" + account_dz + ",seed=" + seed + ",key=" + key + ",dest=" + dest + ",content=" + content);
        System.err.println("畅想空间短信发送记录，手机号：" + dest + ",响应结果：" + result + "备用响应结果：" + resultRes + "，内容：" + URLDecoder.decode(content, "UTF-8"));
    }


}
