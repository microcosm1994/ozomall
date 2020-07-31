package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("sys_roles")
public class RoleDto {
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "角色名称")
    private String name;
}
