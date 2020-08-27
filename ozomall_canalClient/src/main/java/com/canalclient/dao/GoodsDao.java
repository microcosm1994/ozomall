package com.canalclient.dao;


import com.canalclient.dto.GoodsDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Mapper
public interface GoodsDao extends ElasticsearchRepository<GoodsDto, Integer> {

}