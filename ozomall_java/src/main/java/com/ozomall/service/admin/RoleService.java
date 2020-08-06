package com.ozomall.service.admin;

import com.ozomall.entity.Result;
import com.ozomall.entity.admin.RoleDto;

public interface RoleService {
    /**
     * 添加用户角色
     * @param form
     */
    Result addRole(RoleDto form);

    /**
     * 修改用户角色
     * @param form
     */
    Result putRole(RoleDto form);

    /**
     * 获取用户角色
     * @param form
     */
    Result getRole(RoleDto form);

    /**
     * 删除用户角色
     * @param form
     */
    Result delRole(RoleDto form);
}
