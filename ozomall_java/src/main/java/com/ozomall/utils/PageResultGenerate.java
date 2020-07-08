package com.ozomall.utils;

import com.ozomall.entity.PageResult;

import java.util.List;

public class PageResultGenerate {
    public PageResult genPageResult(List list, int page, int size, int total) {
        PageResult pageResult = new PageResult();
        pageResult.setList(list);
        pageResult.setPage(page);
        pageResult.setSize(size);
        pageResult.setTotal(size);
        return pageResult;
    }
}
