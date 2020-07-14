package com.ozomall.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ClassifyEnum {
    brand(0, "品牌"),
    series(1, "系列"),
    shoes(2, "球鞋"),
    fashion(3, "潮搭"),
    watch(4, "手表"),
    accessories(5, "配饰"),
    bags(6, "箱包"),
    fashionplay(7, "潮玩"),
    digital(8, "数码"),
    hea(9, "家电"),
    makeup(10, "美妆"),
    furnishing(11, "家居"),
    automobile(12, "汽车");

    ClassifyEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    @EnumValue
    private int code;
    @JsonValue
    private String descp;
}
