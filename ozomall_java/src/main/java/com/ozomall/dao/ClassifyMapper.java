package com.ozomall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ozomall.entity.ClassifyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassifyMapper extends BaseMapper<ClassifyDto> {
    List<ClassifyDto> childrenList(int id);
}
