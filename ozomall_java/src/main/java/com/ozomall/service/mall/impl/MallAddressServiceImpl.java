package com.ozomall.service.mall.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ozomall.dao.mall.MallAddressMapper;
import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallAddressDto;
import com.ozomall.service.mall.MallAddressService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        QueryWrapper<MallAddressDto> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", form.getUserId());
        List<MallAddressDto> rows = mallAddressMapper.selectList(wrapper);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("失败！");
        }
    }
}
