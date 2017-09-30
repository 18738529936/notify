package com.duanrong.notify.business.config.service;

import com.duanrong.notify.business.config.dao.ConfigDao;
import com.duanrong.notify.business.config.model.Config;
import com.duanrong.util.jedis.DRJedisCacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigService {

    @Autowired
    private ConfigDao configDao;

    private static final String CONFIG = "sys_config";

    public Config get(String id) {

        if (null != id) {
            String value = DRJedisCacheUtil
                    .hget(CONFIG, id).get(id);
            Config config = new Config();
            if (StringUtils.isBlank(value)) {
                config = configDao.get(id);
                if (config != null) {
                    value = config.getValue();
                    Map<String, Object> map = new HashMap<>();
                    map.put((String) id, value);
                    DRJedisCacheUtil.hset(CONFIG, map);
                }
            } else {
                config.setId((String) id);
                config.setValue(value.replace("\"", ""));
            }
            return config;
        }
        return null;
    }

}
