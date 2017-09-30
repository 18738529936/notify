package com.duanrong.notify.business.user.dao;

import com.duanrong.notify.business.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select(value = "SELECT *, id AS user_id FROM user WHERE mobile_number = #{mobileNumber}")
    User getUserByMobileNumber(String mobileNumber);

    @Select(value = "SELECT *, id AS user_id FROM user WHERE id = #{id}")
    User get(String id);

}
