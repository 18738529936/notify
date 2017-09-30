package com.duanrong.notify.business.email.service;

import com.duanrong.notify.util.Log;
import com.duanrong.notify.util.MyStringUtils;
import com.duanrong.util.PropReader;
import com.duanrong.util.mail.MailSendInfo;
import com.duanrong.util.mail.SimpleEmailSend;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

/**
 * 邮件发送器
 *
 * @author guohuan
 * @version 2016年12月15日
 */
@Service
public class EmailSender {

    PropReader reader = new PropReader("/config.properties");
    @Resource
    Log log;

    /*
     * 发送邮件
     */
    public void sendEmail(String subject, String content, String mailtos) throws UnsupportedEncodingException {
        StringBuffer tomail = new StringBuffer("");
        MailSendInfo sendInfo = new MailSendInfo();
        sendInfo.setFromAddress(reader.readProperty("mail_from"));
        sendInfo.setMailServerHost(reader.readProperty("mail_smtp"));
        sendInfo.setPassword(reader.readProperty("mail_password"));
        sendInfo.setUserName(reader.readProperty("mail_from"));
        sendInfo.setContent(content);
        sendInfo.setSubject(MimeUtility.encodeText(subject, MimeUtility.mimeCharset("gb2312"), null));
        SimpleEmailSend send = new SimpleEmailSend();
        try {
            //若mail不为空则给传过来的mail的邮箱发送
            if (MyStringUtils.isNotAnyEmpty(mailtos)) {
                String[] mails = mailtos.split(",");
                for (String to : mails) {
                    //判断是否符合邮箱格式，符合则发送，不符合则不发
                    if (MyStringUtils.mailchick(to)) {
                        tomail.append(to);
                        tomail.append(",");
                    }
                }
            }
            //发送给配置文件中配置的邮箱
            tomail.append(reader.readProperty("mail_to"));
            sendInfo.setToAddress(tomail.toString());
            send.sendHtmlMail(sendInfo);
        } catch (MessagingException e) {
            log.errLog("收件人：" + mailtos + "邮件标题" + subject + "发送失败", e);
        }
    }
}  
      
  

	