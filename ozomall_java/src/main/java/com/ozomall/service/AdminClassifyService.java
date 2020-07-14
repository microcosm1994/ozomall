package com.ozomall.service;

import com.ozomall.entity.AdminClassifyDto;
import com.ozomall.entity.Result;

public interface AdminClassifyService {

    /**
     * 新建分类
     */
    Result addClassify(AdminClassifyDto form);

    /**
     * 查询分类
     */
    Result queryClassify(AdminClassifyDto form);
}
