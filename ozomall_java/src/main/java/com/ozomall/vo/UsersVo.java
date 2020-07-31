package com.ozomall.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ozomall.entity.RoleDto;
import com.ozomall.entity.UserDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UsersVo extends UserDto {
    @JsonIgnore
    private String passWord;
    @ApiModelProperty(value = "角色信息")
    private RoleDto role;
}
