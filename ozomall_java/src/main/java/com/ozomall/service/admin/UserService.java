package com.ozomall.service.admin;

import com.ozomall.entity.Result;
import com.ozomall.entity.UserDto;


public interface UserService {
    /**
     * 登陆
     *
     * @param userName 账号
     * @param passWord 密码
     */
    Result login(String userName, String passWord);

    /**
     * 获取用户信息
     *
     * @param token 用户token
     */
    Result getUserInfo(String token);

    /**
     * 获取用户列表
     *
     * @param form
     */
    Result getUserList(UserDto form);


    /**
     * 添加新用户
     *
     * @param form
     */
    Result addUser(UserDto form);

    /**
     * 修改用户
     *
     * @param form
     */
    Result putUser(UserDto form);

    /**
     * 删除用户
     *
     * @param form
     */
    Result delUser(UserDto form);
}
