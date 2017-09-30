package com.duanrong.notify.business.sms.dao;

import com.duanrong.notify.business.sms.model.UserMessageTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMessageTemplateDao {

    @Select(value = "SELECT * FROM user_message_template WHERE id = #{id} limit 1")
    UserMessageTemplate get(String id);

}
