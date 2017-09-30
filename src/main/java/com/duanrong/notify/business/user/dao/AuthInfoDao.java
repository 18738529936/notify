package com.duanrong.notify.business.user.dao;

import com.duanrong.notify.business.user.model.AuthInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AuthInfoDao {

    @Select(value = "SELECT *, id AS authInfo_id FROM phone_auth WHERE mobileNumber = #{mobileNumber} AND authCode = #{authCode} AND status = '0' AND type = #{type}")
    List<AuthInfo> getAuthInfo(@Param(value = "mobileNumber") String mobileNumber, @Param(value = "authCode") String authCode, @Param(value = "type") String type);

    @Select(value = "DELETE FROM phone_auth WHERE id = #{id}")
    void delete(String id);
}
