package com.ozomall.service.mall;

import com.aliyuncs.exceptions.ClientException;
import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallUserDto;
import com.ozomall.entity.mall.MallUserSettingDto;

public interface MallUserService {

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */
    Result sendMessage(String phone) throws ClientException;

    /**
     * 手机号登陆
     *
     * @param user 用户信息
     */
    Result phoneLogin(MallUserDto user);

    /**
     * 微信登陆
     *
     * @param user 用户信息
     */
    Result wxLogin(MallUserDto user);

    /**
     * 查询
     *
     * @param user 用户信息
     */
    Result getUser(MallUserDto user);

    /**
     * 获取用户设置
     */
    Result getSettings(MallUserSettingDto form);

    /**
     * 设置用户设置
     */
    Result setSettings(MallUserSettingDto form);
}
