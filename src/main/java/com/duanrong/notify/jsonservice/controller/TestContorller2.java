package com.duanrong.notify.jsonservice.controller;

import com.duanrong.notify.jsonservice.handler.Sign;
import com.duanrong.notify.util.FastJsonUtil;
import com.duanrong.util.client.DRHTTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TestContorller2 {

    @Test
    public void test() throws IOException {
        String url = "http://localhost:8080/test/sms.do";
        String timestamp = Long.toString(new Date().getTime());
        HashMap<String, Object> map = new HashMap<String, Object>();
        /*map.put("userId", "QR3M32RzeiA3brff");
        map.put("realName", "赵倩");
        map.put("idCardType", "PRC_ID");
        map.put("userRole", "INVESTOR");
        map.put("idCardNo", "411381199210276113");
        map.put("bankcardNo", "6217000830000123038");
        map.put("mobile", "18738529936");
        map.put("authList", new String[]{"TENDER"});*/
        map.put("password", "123456");
        map.put("loanId", "123");
        String specifyJson = FastJsonUtil.objToJson(map);
        String data = new String(Base64.encode(specifyJson.getBytes()), "utf-8");
        String version = "1.0.0";
        String source = "pc";
        String str = timestamp + "|" + source + "|" + version + "|" + data;
        String sign = Sign.sign(str, "duanrongf0f22ac60d07407cfb7c587f9cab");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("version", version));
        params.add(new BasicNameValuePair("source", source));
        params.add(new BasicNameValuePair("timestamp", timestamp));
        params.add(new BasicNameValuePair("sign", sign));
        params.add(new BasicNameValuePair("data", data));
        String result = DRHTTPClient.sendHTTPRequestPostToString(url,
                new BasicHeader[0], params);
        System.out.println(result);

    }

    @org.junit.Test
    public void test1() throws IOException {
        String url = "http://localhost:8080/account/queryCgtUserInfo.do";
        String timestamp = Long.toString(new Date().getTime());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userId", "QR3M32RzeiA3brff");
        String specifyJson = FastJsonUtil.objToJson(map);
        String data = new String(Base64.encode(specifyJson.getBytes()), "utf-8");
        String version = "1.0.0";
        String source = "pc";
        String str = timestamp + "|" + source + "|" + version + "|" + data;
        String sign = Sign.sign(str, "duanrongf0f22ac60d07407cfb7c587f9cab");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("version", version));
        params.add(new BasicNameValuePair("source", source));
        params.add(new BasicNameValuePair("timestamp", timestamp));
        params.add(new BasicNameValuePair("sign", sign));
        params.add(new BasicNameValuePair("data", data));
        String result = DRHTTPClient.sendHTTPRequestPostToString(url,
                new BasicHeader[0], params);
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        /*try {
        String url = "http://localhost:8080/test/sms.do";
		String timestamp = Long.toString(new Date().getTime());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", "repay");
		String specifyJson = FastJsonUtil.objToJson(map);
		String data;		
		data = new String(Base64.encode(specifyJson.getBytes()), "utf-8");		
		String version = "1.0.0";
		String source = "soa";
		String str = timestamp + "|" + source + "|" + version + "|" + data;
		String sign = Sign.sign(str, "duanrongf0f22ac60d07407cfb7c587f9cab");

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("version", version));
		params.add(new BasicNameValuePair("source", source));
		params.add(new BasicNameValuePair("timestamp", timestamp));
		params.add(new BasicNameValuePair("sign", sign));
		params.add(new BasicNameValuePair("data", data));
		String result = DRHTTPClient.sendHTTPRequestPostToString(url,
				new BasicHeader[0], params);		
		System.out.println(result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*//调用发送email接口
		try {
			String url = "http://localhost:8080/sms/sendEmail.do";
			String timestamp = Long.toString(new Date().getTime());
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("subject", "邮件测试");
			map.put("content", "赶快完成邮件通知系统");
			map.put("mailtos", "yumin@duanrong.com");
			String specifyJson = FastJsonUtil.objToJson(map);
			String data;		
			data = new String(Base64.encode(specifyJson.getBytes()), "utf-8");		
			String version = "1.0.0";
			String source = "soa";
			String str = timestamp + "|" + source + "|" + version + "|" + data;
			String sign = Sign.sign(str, "duanrongf0f22ac60d07407cfb7c587f9cab");

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("version", version));
			params.add(new BasicNameValuePair("source", source));
			params.add(new BasicNameValuePair("timestamp", timestamp));
			params.add(new BasicNameValuePair("sign", sign));
			params.add(new BasicNameValuePair("data", data));
			String result = DRHTTPClient.sendHTTPRequestPostToString(url,
					new BasicHeader[0], params);		
			System.out.println(result);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		/*//调用发送自定义短信接口
				try {
					String url = "http://localhost:8080/sms/sendSmsCustomize.do";
					String timestamp = Long.toString(new Date().getTime());
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("type", "");
					map.put("channel", "emay_sales");
					map.put("mobiles", "18210132758");
					map.put("content", "尊敬的客户，敏敏，你账户里有2张加息券。");
					String specifyJson = FastJsonUtil.objToJson(map);
					String data;		
					data = new String(Base64.encode(specifyJson.getBytes()), "utf-8");		
					String version = "1.0.0";
					String source = "soa";
					String str = timestamp + "|" + source + "|" + version + "|" + data;
					String sign = Sign.sign(str, "duanrongf0f22ac60d07407cfb7c587f9cab");

					List<NameValuePair> params = new ArrayList<>();
					params.add(new BasicNameValuePair("version", version));
					params.add(new BasicNameValuePair("source", source));
					params.add(new BasicNameValuePair("timestamp", timestamp));
					params.add(new BasicNameValuePair("sign", sign));
					params.add(new BasicNameValuePair("data", data));
					String result = DRHTTPClient.sendHTTPRequestPostToString(url,
							new BasicHeader[0], params);		
					System.out.println(result);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
        //调用模板短信接口
        try {
            String url = "http://localhost:8080/sms/sendSmsCustomize.do";
            String timestamp = Long.toString(new Date().getTime());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("type", "withdraw_cash_sms");
            map.put("channel", "yesxin");
            map.put("content", "中文");
            map.put("mobiles", "18738529936");
            String specifyJson = FastJsonUtil.objToJson(map);
            String data;
            data = new String(Base64.encode(specifyJson.getBytes()), "utf-8");
            String version = "1.0.0";
            String source = "soa";
            String str = timestamp + "|" + source + "|" + version + "|" + data;
            String sign = Sign.sign(str, "duanrongf0f22ac60d07407cfb7c587f9cab");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("version", version));
            params.add(new BasicNameValuePair("source", source));
            params.add(new BasicNameValuePair("timestamp", timestamp));
            params.add(new BasicNameValuePair("sign", sign));
            params.add(new BasicNameValuePair("data", data));
            String result = DRHTTPClient.sendHTTPRequestPostToString(url, new BasicHeader[0], params);
            //String result2 =DRHTTPClient.sendHTTPRequestGetToString(url, new BasicHeader[0], params);
            //System.out.println(result2);
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
