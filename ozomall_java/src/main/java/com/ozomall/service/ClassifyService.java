package com.ozomall.service;

import com.ozomall.entity.ClassifyDto;
import com.ozomall.entity.Result;

public interface ClassifyService {

    /**
     * 新建分类
     */
    Result addClassify(ClassifyDto form);

    /**
     * 查询分类
     */
    Result queryClassify(ClassifyDto form);

    /**
     * 查询2、3级分类
     */
    Result queryChildrenList(ClassifyDto form);

}
