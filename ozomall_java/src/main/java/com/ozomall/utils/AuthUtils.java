package com.ozomall.utils;

import com.alibaba.fastjson.JSON;
import com.ozomall.entity.admin.AdminUserDto;

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
        tokenVal.put("role", userInfo.getRole());
        String jsonTokenValue = JSON.toJSONString(tokenVal);
        return jsonTokenValue;
    }
}
