package com.ozomall.dao;

import com.ozomall.entity.UserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminUserMapper {
    public UserDto login(@Param("userName") String userName);

    public UserDto find(@Param("userName") String userName, @Param("nickName") String nickName, @Param("role") Integer role, @Param("phone") String phone);

    public int add(UserDto user);

    public int deleteById(Long id);

    public List<UserDto> findAll();

    public UserDto findById(Long id);

}
