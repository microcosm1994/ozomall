package com.ozomall.vo.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozomall.entity.admin.RoleDto;
import com.ozomall.entity.admin.AdminUserDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UsersVo extends AdminUserDto {
    @JsonIgnore
    private String passWord;
    @ApiModelProperty(value = "角色信息")
    private RoleDto role;
}
