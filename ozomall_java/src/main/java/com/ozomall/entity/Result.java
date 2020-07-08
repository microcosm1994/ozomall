package com.ozomall.entity;

import lombok.Data;

@Data
public class Result<T> {
    /**
     * 返回码
     */
    private Integer code;

    /**
     * 数据
     */
    private T data;

    /**
     * 消息
     */
    private String msg;
}
