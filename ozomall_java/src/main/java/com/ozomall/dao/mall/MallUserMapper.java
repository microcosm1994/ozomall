package com.ozomall.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ozomall.entity.mall.MallUserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MallUserMapper extends BaseMapper<MallUserDto> {
}
