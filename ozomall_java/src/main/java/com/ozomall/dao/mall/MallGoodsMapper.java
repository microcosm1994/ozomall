package com.ozomall.dao.mall;

import com.ozomall.entity.GoodsDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MallGoodsMapper extends ElasticsearchRepository<GoodsDto, Integer> {

}
