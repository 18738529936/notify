
package com.duanrong.notify.util;

import com.duanrong.util.client.DRHTTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TestSms {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SmsHttpClient sms = new SmsHttpClient();
        //sms.sendEmail("测试email", "测试email", "1192867050@qq.com");
//	     sms.sendSms("18738529936", "000", "unbindcard_fail");
//	     sms.sendSms("18738529936", "测试短信发送，2017年2月27日15:24:29", null, null);
//	     sms.sendSms("18738529936", "亲，您已成功注册短融网！60元新手红包已到账，点击 t.cn/R4L4Usl 专享9.5%年化收益新手标", null,null);
//	     sms.sendSms("18738529936", "亲，您已成功注册短融网！60元新手红包已到账，点击 t.cn/R4L4Usl 专享9.5%年化收益新手标", null,"chuanglan_sales");
//	     sms.sendSms("18738529936", "亲，您已成功注册短融网！60元新手红包已到账，点击 t.cn/R4L4Usl 专享9.5%年化收益新手标", null,"chuanglan");
//	     sms.sendSms("18738529936", "您的验证码是：744959，请尽快输入，为保证账户安全，请不要泄露给他人。", null,"chuanglan");
//	     sms.sendSms("18738529936", "亲，您已成功注册短融网！60元新手红包已到账，点击 t.cn/R4L4Usl 专享9.5%年化收益新手标", null,null);
        sms.sendSms("18738529936", "2017年3月1日" + "," + "测试项目"
                + "," + ArithUtil.round(10000.21, 2), "give_money_to_borrower_to_investor");
//	     String content = "小主，发现您有未使用的优惠券，2天后过期，记得及时使用哦，回TD退订";
//	     sms.sendSms("18738529936", content, null, "chuanglan_sales");
//	     try {
//			sendSM("N3833682", "xDhzrOnGs47e78", "18738529936", "亲，您已成功注册短融网！60元新手红包已到账，点击 t.cn/R4L4Usl 专享9.5%年化收益新手标", true, null, null);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    }

    private static void sendSM(String account, String pswd, String mobile, String msg,
                               boolean needstatus, String product, String extno)
            throws IOException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("un", account));
        params.add(new BasicNameValuePair("pw", pswd));
        params.add(new BasicNameValuePair("phone", mobile));
        params.add(new BasicNameValuePair("rd", needstatus ? "1" : "0"));
        System.out.println("编码前的msg：" + msg);
        msg = URLEncoder.encode(msg, "UTF-8");
        System.out.println("编码后的msg：" + msg);
        params.add(new BasicNameValuePair("msg", msg));
        params.add(new BasicNameValuePair("product", product));
        params.add(new BasicNameValuePair("ex", extno));
        String result = DRHTTPClient.sendHTTPRequestPostToString("http://sms.253.com/msg/send", new BasicHeader[0], params, "UTF-8");
        System.out.println("短信发送记录，手机号：" + mobile + ",响应结果：" + result + "，内容：" + msg);
    }
}

