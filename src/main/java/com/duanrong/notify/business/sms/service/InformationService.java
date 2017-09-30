package com.duanrong.notify.business.sms.service;

import com.duanrong.notify.business.sms.dao.InformationDao;
import com.duanrong.notify.business.sms.model.Information;
import com.duanrong.notify.business.user.dao.UserDao;
import com.duanrong.notify.util.IdGenerator;
import com.duanrong.notify.util.ShortUrlGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class InformationService {

    @Resource
    InformationDao informationDao;

    @Resource
    UserDao userDao;

    public void sendInformation(String userId, String title, String content) {
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(content)) {
            String id = ShortUrlGenerator
                    .shortUrl(IdGenerator.randomUUID())
                    + ShortUrlGenerator.shortUrl(IdGenerator.randomUUID());
            Information information = new Information();
            information.setId(id);
            information.setUsername(userId);
            information.setTitle(title);
            information.setContent(content);
            // 1 为显示
            information.setStatus((byte) 1);
            information.setIsRead((byte) 0);
            information.setTime(new Date());
            informationDao.insert(information);
        }
    }
}
