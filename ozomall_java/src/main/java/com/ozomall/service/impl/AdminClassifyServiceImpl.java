package com.ozomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.ozomall.dao.AdminClassifyMapper;
import com.ozomall.entity.AdminClassifyDto;
import com.ozomall.entity.Result;
import com.ozomall.service.AdminClassifyService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminClassifyServiceImpl implements AdminClassifyService {
    @Resource
    private AdminClassifyMapper adminClassifyMapper;

    /**
     * 添加分类
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
     * 查询类别分组
     */
    @Override
    public Result queryClassify(AdminClassifyDto form) {
        Map<SFunction<AdminClassifyDto, ?>, Object> params = new HashMap<>();
        LambdaQueryWrapper<AdminClassifyDto> wrapper = new LambdaQueryWrapper<>();
        params.put(AdminClassifyDto::getName, form.getName());
        params.put(AdminClassifyDto::getParentId, form.getParentId());
        params.put(AdminClassifyDto::getClassifyLevel, form.getClassifyLevel());
        wrapper.allEq(params, false);
        List<AdminClassifyDto> rows = adminClassifyMapper.selectList(wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("数据获取失败");
        }
    }

}
