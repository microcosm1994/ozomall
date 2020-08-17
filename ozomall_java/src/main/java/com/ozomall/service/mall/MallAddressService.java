package com.ozomall.service.mall;

import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallAddressDto;

public interface MallAddressService {
    /**
     * 添加地址
     */
    Result addAddress(MallAddressDto phone);

    /**
     * 修改地址
     */
    Result putAddress(MallAddressDto phone);

    /**
     * 获取地址
     */
    Result getAddress(MallAddressDto phone);

    /**
     * 根据id获取地址
     */
    Result getAddressById(MallAddressDto phone);

    /**
     * 获取地址
     */
    Result delAddress(MallAddressDto phone);
}
