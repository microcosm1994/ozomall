package com.ozomall.service.mall;

import com.aliyuncs.exceptions.ClientException;
import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallUserDto;

public interface MallUserService {

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */
    Result sendMessage(String phone) throws ClientException;

    /**
     * 登陆
     *
     * @param user 用户信息
     */
    Result login(MallUserDto user);

    /**
     * 查询
     *
     * @param user 用户信息
     */
    Result getUser(MallUserDto user);
}
