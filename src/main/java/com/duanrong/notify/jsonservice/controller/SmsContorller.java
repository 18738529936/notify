package com.duanrong.notify.jsonservice.controller;

import com.alibaba.druid.util.StringUtils;
import com.duanrong.notify.business.email.service.EmailSender;
import com.duanrong.notify.business.sms.service.SmsService;
import com.duanrong.notify.jsonservice.handler.RequestParameter;
import com.duanrong.notify.util.IPUtil;
import com.duanrong.notify.util.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/sms", method = RequestMethod.POST)
public class SmsContorller extends BaseController {
    @Resource
    SmsService smsService;
    @Resource
    EmailSender emailSender;
    @Resource
    Log log;

    /**
     * 发送邮件的接口
     *
     * @param subject 主题
     * @param content 内容
     * @param mailtos 收件人
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public void sendEmail(@RequestParameter String subject,
                          @RequestParameter String content, @RequestParameter String mailtos, HttpServletRequest req) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String source = req.getParameter("source");
        String version = req.getParameter("version");
        String Ip = IPUtil.getIpAddr(req);

        if (StringUtils.isEmpty(subject) || StringUtils.isEmpty(content)
                ) {
            log.errLog("邮件发送失败", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "邮件标题" + subject + "邮件内容"
                    + content + "参数有null值");
        } else {
            try {
                content = "[请求服务器Ip:" + Ip + "，来源：" + source + "。]<br>" + content;
                emailSender.sendEmail(subject, content, mailtos);
                log.infoLog("邮件发送成功", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "邮件主题：" + subject + "收件人" + mailtos
                        + "发送成功");
            } catch (Exception e) {
                log.errLog("邮件发送失败", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "收件人：" + mailtos + "邮件标题" + subject + "发送失败，失败原因" + e);
            }
        }
    }

    /**
     * 发送模板短信和站内信的接口
     *
     * @param mobiles 手机号
     * @param params  模板要替换的参数
     * @param type    业务类型
     */
    @RequestMapping(value = "/sendSmsTemplate", method = RequestMethod.POST)
    @ResponseBody
    public void sendSmsTemplate(@RequestParameter String mobiles,
                                @RequestParameter String params, @RequestParameter String type, HttpServletRequest req) {
        String source = req.getParameter("source");
        String version = req.getParameter("version");
        String Ip = IPUtil.getIpAddr(req);

        if (StringUtils.isEmpty(mobiles) || StringUtils.isEmpty(params)
                || StringUtils.isEmpty(type)) {
            log.errLog("模板短信发送失败", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "手机号：" + mobiles + "模板参数" + params + "业务类型"
                    + type + "参数有null值");
        } else {
            try {
                smsService.sendSmsTemplate(mobiles, params, type);
                log.infoLog("模板短信发送成功", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "手机号：" + mobiles + "模板参数{" + params
                        + "}发送成功");
            } catch (Exception e) {
                log.errLog("模板短信发送成功", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "模板短信发送失败,手机号：" + mobiles + "模板参数" + params + "业务类型"
                        + type + "失败原因" + e);
            }
        }
    }

    /**
     * 发送自定义站内信接口
     *
     * @param userId  用户编号
     * @param content 站内信内容
     * @param title   业务类型可以为空
     */
    @RequestMapping(value = "/sendInformationCustomize", method = RequestMethod.POST)
    @ResponseBody
    public void sendInformationCustomize(@RequestParameter String userId,
                                         @RequestParameter String title, @RequestParameter String content,
                                         HttpServletRequest req) {
        String source = req.getParameter("source");
        String version = req.getParameter("version");
        String Ip = IPUtil.getIpAddr(req);

        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) {
            log.errLog("自定义站内信发送失败", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "userId：" + userId + "模板参数" + content
                    + "业务类型" + title + "参数有null值");
        } else {
            try {
                smsService.sendInformationCustomize(userId, title, content);
                log.infoLog("自定义站内信发送成功", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "userId：" + userId + "站内信内容{" + content
                        + "}发送成功");
            } catch (Exception e) {
                log.errLog("自定义站内信发送失败", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "userId：" + userId + "站内信内容" + content
                        + "失败原因" + e);
            }
        }
    }

    /**
     * 发送自定义短信接口
     *
     * @param mobiles 手机号
     * @param content 短信内容
     * @param type    业务类型可以为空
     * @param channel 短信渠道
     */
    @RequestMapping(value = "/sendSmsCustomize", method = RequestMethod.POST)
    @ResponseBody
    public void sendSmsCustomize(@RequestParameter String mobiles,
                                 @RequestParameter String content, @RequestParameter String type,
                                 @RequestParameter String channel, HttpServletRequest req) {
        String source = req.getParameter("source");
        String version = req.getParameter("version");
        String Ip = IPUtil.getIpAddr(req);

        if (StringUtils.isEmpty(mobiles) || StringUtils.isEmpty(content)) {
            log.errLog("自定义短信发送失败", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "手机号：" + mobiles + "模板参数" + content
                    + "业务类型" + type + "参数有null值");
        } else {
            try {
                smsService.sendSmsCustomize(mobiles, content, type, channel);
                log.infoLog("自定义短信发送成功", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "手机号：" + mobiles + "短信内容{" + content
                        + "}发送成功");
            } catch (Exception e) {
                log.errLog("自定义短信发送失败", "[请求服务器Ip:" + Ip + "，来源：" + source + "。]" + "手机号：" + mobiles + "模板参数" + content
                        + "业务类型" + type + "失败原因" + e);
            }
        }
    }
}
