package com.ozomall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.entity.GoodsAttrDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsAttrMapper extends BaseMapper<GoodsAttrDto> {
    List<GoodsAttrDto> goodsAttr(Page page, GoodsAttrDto form);
}
