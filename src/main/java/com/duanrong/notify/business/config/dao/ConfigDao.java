package com.duanrong.notify.business.config.dao;

import com.duanrong.notify.business.config.model.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ConfigDao {

    @Select(value = "SELECT * FROM config where id=#{id}")
    Config get(@Param(value = "id") String id);

}
