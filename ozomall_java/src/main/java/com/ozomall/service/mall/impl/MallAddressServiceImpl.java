package com.ozomall.service.mall.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.ozomall.dao.mall.MallAddressMapper;
import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallAddressDto;
import com.ozomall.service.mall.MallAddressService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MallAddressServiceImpl implements MallAddressService {

    @Resource
    private MallAddressMapper mallAddressMapper;

    /**
     * 添加地址
     */
    @Override
    public Result addAddress(MallAddressDto form) {
        int row = mallAddressMapper.insert(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult(row);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }


    /**
     * 修改地址
     */
    @Override
    public Result putAddress(MallAddressDto form) {
        int row = mallAddressMapper.updateById(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }

    /**
     * 获取地址
     */
    @Override
    public Result getAddress(MallAddressDto form) {
        LambdaQueryWrapper<MallAddressDto> wrapper = new LambdaQueryWrapper<>();
        Map<SFunction<MallAddressDto, ?>, Object> map = new HashMap<>();
        map.put(MallAddressDto::getId, form.getId());
        map.put(MallAddressDto::getUserId, form.getUserId());
        wrapper.allEq(map, false);
        List<MallAddressDto> rows = mallAddressMapper.selectList(wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }

    /**
     * 根据id获取地址
     */
    @Override
    public Result getAddressById(MallAddressDto form) {
        MallAddressDto row = mallAddressMapper.selectById(form.getId());
        if (row != null) {
            return ResultGenerate.genSuccessResult(row);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }

    /**
     * 删除地址
     */
    @Override
    public Result delAddress(MallAddressDto form) {
       int row = mallAddressMapper.deleteById(form.getId());
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }
}
