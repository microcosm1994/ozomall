package com.ozomall.dao.mall;

import com.ozomall.entity.GoodsDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Mapper
public interface MallGoodsMapper extends ElasticsearchRepository<GoodsDto, Integer> {

}
