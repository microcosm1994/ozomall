package com.ozomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ozomall.dao.AdminClassifyMapper;
import com.ozomall.entity.AdminClassifyDto;
import com.ozomall.entity.Result;
import com.ozomall.service.AdminClassifyService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminClassifyServiceImpl implements AdminClassifyService {
    @Resource
    private AdminClassifyMapper adminClassifyMapper;

    /**
     * 新建分类
     */
    @Override
    public Result addClassify(AdminClassifyDto form) {
        int row = adminClassifyMapper.insert(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("新建失败，请重试。");
        }
    }

    /**
     * 查询分类
     */
    @Override
    public Result queryClassify(AdminClassifyDto form) {
        LambdaQueryWrapper<AdminClassifyDto> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminClassifyDto::getName, form.getName());
        List<AdminClassifyDto> rows = adminClassifyMapper.selectList(wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("数据获取失败");
        }
    }
}
