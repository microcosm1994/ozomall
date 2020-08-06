package com.ozomall.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.entity.admin.AdminUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUserDto> {

    IPage<Map> userList(Page page, AdminUserDto form);

    AdminUserDto getUsers(String userName);
}
