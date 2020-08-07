package com.ozomall.service.admin;

import com.ozomall.entity.Result;
import com.ozomall.entity.admin.AdminUserDto;
import com.ozomall.entity.mall.MallUserDto;


public interface AdminUserService {
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
    Result getUserList(AdminUserDto form);


    /**
     * 添加新用户
     *
     * @param form
     */
    Result addUser(AdminUserDto form);

    /**
     * 修改用户
     *
     * @param form
     */
    Result putUser(AdminUserDto form);

    /**
     * 删除用户
     *
     * @param form
     */
    Result delUser(AdminUserDto form);

    /**
     * 获取商城用户列表
     *
     * @param form
     */
    Result getMallUserList(MallUserDto form);
}
