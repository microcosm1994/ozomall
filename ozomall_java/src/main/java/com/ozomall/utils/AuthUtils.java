package com.ozomall.utils;

import com.alibaba.fastjson.JSON;
import com.ozomall.entity.admin.AdminUserDto;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class AuthUtils {
    /**
     * 生成token
     *
     * @param userName 账号
     * @return 返回加密后的账号用作token
     */
    public static String genToken(String userName) {
        Encryption encrypt = new Encryption();
        return encrypt.encrypt(userName);
    }

    /**
     * 设置token
     *
     * @param token    token
     * @param userInfo 用户信息
     */
    public static String setToken(String token, AdminUserDto userInfo) {
        Map tokenVal = new HashMap();
        tokenVal.put("userName", userInfo.getUserName());
        tokenVal.put("role", userInfo.getRoleId());
        String jsonTokenValue = JSON.toJSONString(tokenVal);
        return jsonTokenValue;
    }

    /**
     * 获取ip
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
