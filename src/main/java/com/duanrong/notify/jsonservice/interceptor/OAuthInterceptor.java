package com.duanrong.notify.jsonservice.interceptor;

import com.duanrong.notify.base.error.ErrorCode;
import com.duanrong.notify.business.config.model.Config;
import com.duanrong.notify.business.config.service.ConfigService;
import com.duanrong.notify.config.BusinessEnum;
import com.duanrong.notify.jsonservice.handler.Sign;
import com.duanrong.notify.jsonservice.handler.TerminalEnum;
import com.duanrong.notify.jsonservice.handler.View;
import com.duanrong.notify.util.FastJsonUtil;
import com.duanrong.notify.util.Log;
import com.duanrong.util.jedis.DRJedisCacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 鉴权拦截器
 *
 * @author xiao
 * @datetime 2016年10月26日 下午8:45:42
 */
public class OAuthInterceptor implements HandlerInterceptor {

    // 鉴权密钥
    private String key;

    // 过期时间，请求时间距 服务器当前时间超过timeout,视为无效请求
    private long timeout;

    // 此接口无需拦截
    private String filtration;

    @Resource
    Log log;
    @Resource
    ConfigService configService;

    private static final String API_AUTH_IP = "sys_config";
    private static final String ENCODING = "UTF-8";
    private static final String VER = "1.0.0";

    // IP黑名单
    private static final String BLACKS_IP = "sys_req_blacks";
    public static final String REQ_NUM = "sys_req_num";
    org.apache.commons.logging.Log logger = LogFactory.getLog(OAuthInterceptor.class);

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String[] getFiltration() {
        return filtration.split(",");
    }

    public void setFiltration(String filtration) {
        this.filtration = filtration;
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp,
                           Object obj, ModelAndView mode) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
                             Object obj) throws Exception {
        resp.setCharacterEncoding(ENCODING);
        String timestamp = req.getParameter("timestamp");
        String source = req.getParameter("source");
        String version = req.getParameter("version");
        String data = req.getParameter("data");
        String sign = req.getParameter("sign");
        View view = new View();
        view.setResponseTime(new Date());
        view.setType(TerminalEnum.app);
        view.setVersion(VER);

        // 获取请求者ip
        String ip = "";
        if (!StringUtils.isBlank(source)) {
            source = source.toLowerCase();
            if (source.contains("ios") || source.contains("android")
                    || source.contains("app")) {
                ip = getIP(req);
            } else {
                ip = req.getParameter("ip");
            }
        } else
            ip = getIP(req);
        // ip黑名单检验
        if (authenticationIP(req, ip)) {
            view.setError(ErrorCode.RequestTooMany);
            Writer write = resp.getWriter();
            write.write(FastJsonUtil.objToJsonWriteNullToEmpty(view));
            write.flush();
            write.close();
            return false;
        }

        // 接口签名鉴权
        String[] filtrations = getFiltration();
        for (String filtration : filtrations) {
            if (req.getRequestURI().contains(filtration))
                return true;
        }

        long currentimes = new Date().getTime();
        if (null == timestamp || ("").equals(timestamp)) {
            this.addBlacks(ip);
            view.setError(ErrorCode.TIMESTAMP_INVALID);
            Writer write = resp.getWriter();
            write.write(FastJsonUtil.objToJsonWriteNullToEmpty(view));
            write.flush();
            write.close();
            return false;
        } else if (Math.abs(currentimes - Long.parseLong(timestamp)) > timeout) {
            this.addBlacks(ip);
            view.setError(ErrorCode.TIMESTAMP_EXPIRE);
            Writer write = resp.getWriter();
            write.write(FastJsonUtil.objToJsonWriteNullToEmpty(view));
            write.flush();
            write.close();
            return false;
        } else if (null == version || ("").equals(version)) {
            this.addBlacks(ip);
            Writer write = resp.getWriter();
            view.setError(ErrorCode.VERSION_INVALID);
            write.write(FastJsonUtil.objToJsonWriteNullToEmpty(view));
            write.flush();
            write.close();
            return false;
        } else if (null == sign || ("").equals(sign)) {
            this.addBlacks(ip);
            Writer write = resp.getWriter();
            view.setError(ErrorCode.SIGN_INVALID);
            write.write(FastJsonUtil.objToJsonWriteNullToEmpty(view));
            write.flush();
            write.close();
            return false;
        } else {
            String str = timestamp + "|" + source + "|" + version;
            if (data != null)
                str += "|" + data;
            String s = Sign.sign(str, key);
            if (sign.equals(s)) {
                return true;
            } else {
                sign = sign.replaceAll(" ", "+");
                if (sign.equals(s)) {
                    return true;
                } else {
                    logger.debug("--------------------sign: " + sign + "----------------s:" + s);
                    this.addBlacks(ip);
                    Writer write = resp.getWriter();
                    view.setError(ErrorCode.SIGN_INVALID);
                    write.write(FastJsonUtil.objToJsonWriteNullToEmpty(view));
                    write.flush();
                    write.close();
                    return false;
                }
            }
        }
    }

    /**
     * ip给名单校验
     *
     * @param request
     * @return
     */
    private boolean authenticationIP(HttpServletRequest request, String ip) {
        try {
            //验证是否在白名单
            try {
                if (!StringUtils.isBlank(ip)) {
                    String ips = DRJedisCacheUtil
                            .hget(API_AUTH_IP, BusinessEnum.reward.name()).get(
                                    BusinessEnum.reward.name());
                    if (ips == null) {
                        Config config = configService.get(BusinessEnum.reward.name());
                        if (config != null) {
                            ips = config.getValue();
                            Map<String, Object> map = new HashMap<>();
                            map.put(BusinessEnum.reward.name(), ips);
                            DRJedisCacheUtil.hset(API_AUTH_IP, map);
                        }
                    }
                    if (ips != null && ips.contains(ip))
                        return false;
                }
            } catch (Exception e) {
                log.errLog("白名单验证异常", e, 1);
                e.printStackTrace();
            }
            //判断是否在黑名单
            if (!StringUtils.isBlank(ip)
                    && DRJedisCacheUtil.sexists(BLACKS_IP, ip)) {
                Enumeration<String> enu = request.getParameterNames();
                StringBuffer buf = new StringBuffer();
                if (null != enu) {
                    while (enu.hasMoreElements()) {
                        String paraName = enu.nextElement();
                        buf.append(paraName + "="
                                + request.getParameter(paraName) + ";");
                    }
                }
                log.errLog("请求拦截", "requestIP: " + ip
                        + ", 已添加黑名单, requestURL: " + request.getRequestURL()
                        + ", params: " + buf);
                return true;
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    // 获取请求ip
    private String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.contains("192.168.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0)
                ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    /**
     * 添加黑名单
     *
     * @param ip
     */
    private void addBlacks(String ip) {
        try {
            if (!StringUtils.isBlank(ip)) {
                //验证是否在白名单
                String ips = DRJedisCacheUtil
                        .hget(API_AUTH_IP, BusinessEnum.reward.name()).get(
                                BusinessEnum.reward.name());
                if (ips == null) {
                    Config config = configService.get(BusinessEnum.reward.name());
                    if (config != null) {
                        ips = config.getValue();
                        Map<String, Object> map = new HashMap<>();
                        map.put(BusinessEnum.reward.name(), ips);
                        DRJedisCacheUtil.hset(API_AUTH_IP, map);
                    }
                }
                boolean authIpFlag = (ips != null && ips.contains(ip));
                if (!authIpFlag) {
                    ThreadLocal<Integer> ipCount = new ThreadLocal<>();
                    ipCount.set((Integer) DRJedisCacheUtil.hget(REQ_NUM,
                            Integer.class, ip).get(ip));
                    ipCount.set(ipCount.get() == null ? 1 : ipCount.get() + 1);
                    Map<String, String> hash = new HashMap<>();
                    hash.put(ip, "" + ipCount.get());
                    if (DRJedisCacheUtil.exists(REQ_NUM))
                        DRJedisCacheUtil.hsetstr(REQ_NUM, hash);
                    else
                        DRJedisCacheUtil.hsetstr(REQ_NUM, 86400, hash);
                    if (ipCount.get() >= 100)
                        DRJedisCacheUtil.sadd(BLACKS_IP, ip);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}