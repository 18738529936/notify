package com.duanrong.notify.business.sms.dao;

import com.duanrong.notify.business.sms.model.Sms;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsDao {

    @Insert(value = "INSERT INTO sms_history (id, user_id, mobile_number, content, time, type) VALUES (#{id}, #{userId}, #{mobileNumber}, #{content}, #{time}, #{type})")
    void insert(Sms sms);
}
