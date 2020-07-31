package com.ozomall.controller.admin;

import com.ozomall.entity.Result;
import com.ozomall.entity.RoleDto;
import com.ozomall.service.admin.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户角色相关接口")
@Controller
@ResponseBody
@RequestMapping("/admin/role")
public class RoleController {
    @Resource
    RoleService roleService;

    @ApiOperation(value = "添加用户角色")
    @PostMapping("/add")
    public Result addRole (@RequestBody RoleDto form) {
        return roleService.addRole(form);
    }

    @ApiOperation(value = "获取用户角色")
    @GetMapping("/get")
    public Result getRole (RoleDto form) {
        return roleService.getRole(form);
    }
}
