package com.ozomall.dao.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ozomall.entity.admin.AdminUserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUserDto> {

}
