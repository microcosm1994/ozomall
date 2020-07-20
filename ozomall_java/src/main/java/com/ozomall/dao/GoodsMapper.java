package com.ozomall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.entity.GoodsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface GoodsMapper extends BaseMapper<GoodsDto> {
    IPage<Map> goodsList(Page page,GoodsDto form);
}
