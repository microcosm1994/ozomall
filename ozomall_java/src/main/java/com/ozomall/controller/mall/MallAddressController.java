package com.ozomall.controller.mall;

import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallAddressDto;
import com.ozomall.service.mall.MallAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/mall/address")
@ResponseBody
@Api(tags = "商城收货地址相关接口")
public class MallAddressController {
    @Resource
    private MallAddressService mallAddressService;

    @ApiOperation("添加地址")
    @PostMapping("/add")
    public Result addAddress(@RequestBody MallAddressDto form) {
        return mallAddressService.addAddress(form);
    }

    @ApiOperation("修改地址")
    @PostMapping("/put")
    public Result putAddress(@RequestBody MallAddressDto form) {
        return mallAddressService.putAddress(form);
    }

    @ApiOperation("获取地址")
    @GetMapping("/get")
    public Result getAddress(MallAddressDto form) {
        return mallAddressService.getAddress(form);
    }

    @ApiOperation("根据id获取地址")
    @GetMapping("/getById")
    public Result getAddressById(MallAddressDto form) {
        return mallAddressService.getAddressById(form);
    }

    @ApiOperation("删除地址")
    @PostMapping("/del")
    public Result delAddress(@RequestBody MallAddressDto form) {
        return mallAddressService.delAddress(form);
    }
}
