package com.ozomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.DateTimeException;

@Data
@TableName("orders")
public class OrderDto extends PageReqDto {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "关联用户id")
    private Integer userId;

    @ApiModelProperty(value = "关联商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "关联地址id")
    private Integer addressId;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "实际支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "运费")
    private BigDecimal freightAmount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty(value = "支付方式：0->未支付；1->支付宝；2->微信")
    private Integer payType;

    @ApiModelProperty(value = "订单来源：0->微信小程序；1->app订单")
    private Integer sourceType;

    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->待收货；3->已完成；4->已关闭；")
    private Integer status;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Integer del;

    @ApiModelProperty(value = "发货时间")
    private DateTimeException deliveryTime;

    @ApiModelProperty(value = "支付时间")
    private DateTimeException paymentTime;

    @ApiModelProperty(value = "确认收货时间")
    private DateTimeException receiveTime;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
}
