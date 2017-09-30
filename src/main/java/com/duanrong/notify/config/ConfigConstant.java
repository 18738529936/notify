package com.duanrong.notify.config;

import com.duanrong.util.PropReader;

/*
 * config.properties配置
 */
public final class ConfigConstant {

    //properties 读取器
    private static PropReader rader = new PropReader("/config.properties");

    /**
     * 服务器
     */
    public static String SERVERS = rader.readProperty("servers");

    /**
     * 资源服务器
     */
    public static String OSS_SERVER = rader.readProperty("oss_server");
}
