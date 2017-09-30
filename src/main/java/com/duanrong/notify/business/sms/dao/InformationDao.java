package com.duanrong.notify.business.sms.dao;

import com.duanrong.notify.business.sms.model.Information;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InformationDao {

    @Select(value = "SELECT * FROM information WHERE username = #{userId} AND status = 1 ORDER BY time DESC")
    List<Information> getInformationByUserId(String userId);

    @Insert(value = "INSERT INTO information (id, username, time, status, title, content, is_read) VALUES (#{id}, #{username}, #{time}, #{status}, #{title}, #{content}, #{isRead})")
    void insert(Information information);

}
