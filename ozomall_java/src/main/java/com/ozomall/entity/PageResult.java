package com.ozomall.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageResult {
    private int page;
    private int size;
    private int total;
    private List list;
}
