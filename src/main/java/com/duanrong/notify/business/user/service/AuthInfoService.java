package com.duanrong.notify.business.user.service;

import com.duanrong.notify.business.user.dao.AuthInfoDao;
import com.duanrong.notify.business.user.model.AuthInfo;
import com.duanrong.notify.util.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class AuthInfoService {
    @Resource
    AuthInfoDao authInfoDao;

    @Resource
    Log log;

    public boolean operateAuthCode(String mobileNumber, String authCode,
                                   String type) {
        if (StringUtils.isBlank(mobileNumber) || StringUtils.isBlank(authCode)
                || StringUtils.isBlank(type)) {
            return false;
        }
        List<AuthInfo> list = authInfoDao.getAuthInfo(mobileNumber, authCode,
                type);
        AuthInfo ai = null;
        if (list.size() == 1) {
            ai = list.get(0);
        }
        if (ai != null) {
            // 校验验证码是否已失效
            if (ai.getDeadline() != null && ai.getDeadline().before(new Date())) {
                // 删除数据库中已失效的验证码
                authInfoDao.delete(ai.getId());
                return false;
            }
            if (StringUtils.equals(mobileNumber, ai.getMobileNumber())
                    && StringUtils.equals(authCode, ai.getAuthCode())
                    && StringUtils.equals(type, ai.getType())) {
                return true;
            }
        }
        return false;
    }
}