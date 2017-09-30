package com.duanrong.notify.jsonservice.controller;

import com.duanrong.notify.business.email.service.EmailSender;
import com.duanrong.notify.business.sms.service.SmsService;
import com.duanrong.notify.jsonservice.handler.RequestParameter;
import com.duanrong.notify.jsonservice.handler.View;
import com.duanrong.notify.util.RedisCacheKey;
import com.duanrong.util.jedis.DRJedisCacheUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping(value = "/test", method = RequestMethod.POST)
public class TestContorller extends BaseController {
    @Resource
    SmsService smsService;
    @Resource
    EmailSender emailSender;

    @RequestMapping(value = "/test")
    @ResponseBody
    public View test(@RequestParameter String password, @RequestParameter String loanId) {
        View view = getView();
        view.setData(password);
        return view;
    }

    @RequestMapping(value = "/sms")
    @ResponseBody
    public View sms(@RequestParameter String type) {
        View view = getView();
        /*String tempt= smsService.operateTemplate(type);*/
        String tempt = DRJedisCacheUtil.get(RedisCacheKey.SMS_TEMPLATE);
        System.out.println(tempt);
        view.setData(tempt);
        return view;
    }

}
