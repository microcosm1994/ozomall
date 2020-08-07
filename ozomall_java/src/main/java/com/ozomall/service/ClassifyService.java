package com.ozomall.service;

import com.ozomall.entity.ClassifyDto;
import com.ozomall.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ClassifyService {

    /**
     * 新建分类
     */
    Result addClassify(ClassifyDto form);

    /**
     * 上传分类图片
     */
    Result classifyUpload(MultipartFile file) throws IOException;

    /**
     * 查询分类
     */
    Result queryClassify(ClassifyDto form);

    /**
     * 查询2、3级分类
     */
    Result queryChildrenList(ClassifyDto form);

    /**
     * 修改分类
     */
    Result putClassify(ClassifyDto form);

}
