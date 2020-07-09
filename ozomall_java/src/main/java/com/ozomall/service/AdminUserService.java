package com.ozomall.service;

import com.ozomall.entity.Result;
import org.springframework.stereotype.Service;

@Service
public interface AdminUserService {
    /**
     * 登陆
     *
     * @param userName 账号
     * @param passWord 密码
     * @return
     */
    Result login(String userName, String passWord);
}
