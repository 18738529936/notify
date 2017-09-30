package com.duanrong.notify.business.user.service;

import com.duanrong.notify.business.user.dao.UserDao;
import com.duanrong.notify.business.user.model.User;
import com.duanrong.notify.util.Log;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    @Resource
    Log log;

    /**
     * 根据id获取用户
     */
    public User get(String id) {
        return userDao.get(id);
    }

}
