package com.duanrong.notify.config;

import java.util.UUID;


public class IdUtil {

    /**
     * @return
     * @author yinxunzhi
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
