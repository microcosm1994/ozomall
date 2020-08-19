package com.ozomall.vo;

import com.ozomall.entity.OrderDto;
import lombok.Data;

import java.util.Map;

@Data
public class OrdersVo extends OrderDto {
    private Map userInfo;
    private Map addressInfo;
}
