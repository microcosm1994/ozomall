package com.ozomall.service;

import com.ozomall.entity.Result;


public interface AdminUserService {
    /**
     * 登陆
     *
     * @param userName 账号
     * @param passWord 密码
     * @return
     */
    Result login(String userName, String passWord);

    /**
     * 获取用户信息
     *
     * @param token 用户token
     * @return
     */
    Result getUserInfo(String token);
}
