package com.ozomall.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.ozomall.dao.RoleMapper;
import com.ozomall.entity.Result;
import com.ozomall.entity.RoleDto;
import com.ozomall.service.admin.RoleService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleMapper roleMapper;

    /**
     * 添加用户角色
     *
     * @param form
     */
    @Override
    public Result addRole(RoleDto form) {
        int row = roleMapper.insert(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("添加失败");
        }
    }

    /**
     * 获取用户角色
     *
     * @param form
     */
    @Override
    public Result getRole(RoleDto form) {
        LambdaQueryWrapper<RoleDto> wrapper = new LambdaQueryWrapper<>();
        Map<SFunction<RoleDto, ?>, Object> map = new HashMap<>();
        map.put(RoleDto::getName, form.getName());
        map.put(RoleDto::getCode, form.getCode());
        map.put(RoleDto::getType, form.getType());
        wrapper.allEq(map, false);
        List<RoleDto> rows = roleMapper.selectList(wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("获取失败");
        }
    }
}
