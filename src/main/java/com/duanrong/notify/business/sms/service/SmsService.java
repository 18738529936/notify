package com.duanrong.notify.business.sms.service;

import com.duanrong.notify.business.sms.SmsConstants;
import com.duanrong.notify.business.sms.dao.SmsDao;
import com.duanrong.notify.business.sms.dao.UserMessageTemplateDao;
import com.duanrong.notify.business.sms.model.Sms;
import com.duanrong.notify.business.sms.model.UserMessageTemplate;
import com.duanrong.notify.business.user.dao.UserDao;
import com.duanrong.notify.business.user.model.User;
import com.duanrong.notify.util.*;
import com.duanrong.util.jedis.DRJedisCacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*import com.duanrong.util.sms.SMSSend;*/

@Service
public class SmsService {

    @Resource
    UserDao userDao;

    @Resource
    SmsDao smsDao;
    @Resource
    InformationService informationService;
    @Resource
    Log log;

    @Resource
    UserMessageTemplateDao userMessageTemplateDao;

    public void sendSms(String mobiles, String content, String type,
                        String userId, String channel) {
        try {
            if (StringUtils.isNotBlank(mobiles) && StringUtils.isNotBlank(content)) {
                if (StringUtils.isBlank(type)) {
                    type = SmsConstants.WITHOUT;
                }
                String id = ShortUrlGenerator.shortUrl(IdGenerator.randomUUID())
                        + ShortUrlGenerator.shortUrl(IdGenerator.randomUUID());
                SMSSend.batchSends(mobiles, content, channel);
                Sms sms = new Sms();
                sms.setId(id);
                sms.setUserId(userId);
                sms.setMobileNumber(mobiles);
                sms.setContent(content);
                sms.setTime(new Date());
                sms.setType(type);
                smsDao.insert(sms);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.errLog("手机号为" + mobiles + ",业务类型为" + type + "模板短信和站内信发送失败", e);
        }
    }

    public UserMessageTemplate operateTemplate(String type) {
        try {
            UserMessageTemplate userMessageTemplate = (UserMessageTemplate) DRJedisCacheUtil
                    .hget(RedisCacheKey.SMS_TEMPLATE, UserMessageTemplate.class,
                            type).get(type);
            if (userMessageTemplate == null) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("id", type);
                userMessageTemplate = userMessageTemplateDao.get(type);
                if (userMessageTemplate != null) {
                    paramMap = new HashMap<>();
                    paramMap.put(type, userMessageTemplate);
                    DRJedisCacheUtil.hset(RedisCacheKey.SMS_TEMPLATE, paramMap);
                }
            }
            return userMessageTemplate;
        } catch (Exception e) {
            log.errLog("读取短信模板业务类型为" + type + "失败", e);
        }
        /*
         * UserMessageTemplate userMessageTemplate = userMessageTemplateDao
		 * .get(type + "_sms"); String template =
		 * userMessageTemplate.getTemplate();
		 */
        return null;
    }

    public void sendSmsTemplate(String mobiles, String params, String type) {
        if (StringUtils.isNotBlank(mobiles) && StringUtils.isNotBlank(params)) {
            if (StringUtils.isBlank(type)) {
                type = SmsConstants.WITHOUT;
            }
            UserMessageTemplate userMessageTemplate = this
                    .operateTemplate(type);
            try {
                String[] moileList = mobiles.split(",");
                String[] contents = params.split(",");
                // 从redis缓存里面读取
                if (userMessageTemplate != null) {
                    int i = 0;
                    String content = userMessageTemplate.getTemplate();
                    for (String param : contents) {
                        i++;
                        content = StringUtils.replace(content, "#param" + i,
                                param);
                    }
                    for (String moible : moileList) {
                        // 发送模板短信和站内信，必须是系统里面的用户才可以发送，只发模板短信不用判断是否是系统用户
                        User user = userDao.getUserByMobileNumber(moible);
                        String mobileNumber = user.getMobileNumber();
                        if (StringUtils.isNoneBlank(mobileNumber)) {
                            String id = ShortUrlGenerator.shortUrl(IdGenerator
                                    .randomUUID())
                                    + ShortUrlGenerator.shortUrl(IdGenerator
                                    .randomUUID());
                            // 判断是发送模板短信和站内信还是只发送模板短信
                            if (userMessageTemplate.getMessageWay() != null) {
                                if ("smsAndEmail".equals(userMessageTemplate
                                        .getMessageWay())) {
                                    // 调用发送站内信的方法
                                    informationService.sendInformation(
                                            user.getUserId(),
                                            userMessageTemplate.getName(), content);
                                    // 调用短信接口
                                    sendSms(mobileNumber, content, type,
                                            user.getUserId(),
                                            userMessageTemplate.getChannel());
                                } else if ("email".equals(userMessageTemplate
                                        .getMessageWay())) {
                                    // 调用发送站内信的方法
                                    informationService.sendInformation(
                                            user.getUserId(),
                                            userMessageTemplate.getName(), content);
                                } else {
                                    // 调用短信接口
                                    sendSms(mobileNumber, content, type,
                                            user.getUserId(),
                                            userMessageTemplate.getChannel());
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                log.errLog("手机号为" + mobiles + ",业务类型为" + type + "模板短信和站内信发送失败", ex);
            }
        }
    }

    public void sendSmsCustomize(String mobiles, String content, String type,
                                 String channel) {
        if (StringUtils.isNotBlank(mobiles) && StringUtils.isNotBlank(content)) {
            if (StringUtils.isBlank(type)) {
                type = SmsConstants.WITHOUT;
            }
            if (StringUtils.isBlank(channel)) {
                channel = "chuanglan_sale";
            }
            try {
                String[] moileList = mobiles.split(",");
                for (String moible : moileList) {
                    // 发送自定义短信不用判断是否是系统用户
                    String id = ShortUrlGenerator.shortUrl(IdGenerator
                            .randomUUID())
                            + ShortUrlGenerator.shortUrl(IdGenerator
                            .randomUUID());
                    SMSSend.batchSends(moible, content, channel);
                    Sms sms = new Sms();
                    sms.setId(id);
                    User user = userDao.getUserByMobileNumber(moible);
                    if (user != null) {
                        sms.setUserId(user.getUserId());
                    } else {
                        sms.setUserId("NoRegister");
                    }
                    sms.setMobileNumber(mobiles);
                    sms.setContent(content);
                    sms.setTime(new Date());
                    sms.setType(type);
                    smsDao.insert(sms);
                }
            } catch (Exception e) {
                log.errLog("手机号：" + mobiles + "自定义短信内容：" + content + "发送失败", e);
            }
        }
    }

    public void sendInformationCustomize(String userId, String title,
                                         String content) {
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(title)
                && StringUtils.isNotBlank(content)) {
            try {
                // 发送模板短信和站内信，必须是系统里面的用户才可以发送，只发模板短信不用判断是否是系统用户
                User user = userDao.get(userId);
                if (user != null) {
                    // 判断是发送模板短信和站内信还是只发送模板短信
                    // 调用发送站内信的方法
                    informationService.sendInformation(userId, title, content);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                log.errLog(
                        "userId：" + userId + ",标题为" + title + ",内容为" + content + "站内信发送失败",
                        ex);
            }
        }
    }

}
