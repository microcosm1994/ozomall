package com.ozomall.entity;

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
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 当前显示条数
     */
    private Integer size;
    /**
     * 总条数
     */
    private Integer total;

    /**
     * 返回码
     */
    public Integer getResultCode() {
        return code;
    }

    public void setResultCode(Integer code) {
        this.code = code;
    }

    /**
     * 返回数据
     */
    public T getResultData() {
        return data;
    }

    public void setResultData(T data) {
        this.data = data;
    }

    /**
     * 返回消息
     */
    public String getResultMsg() {
        return msg;
    }

    public void setResultMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 当前页
     */
    public Integer getResultPage() {
        return page;
    }

    public void setResultPage(Integer page) {
        this.page = page;
    }

    /**
     * 当前条数
     */
    public Integer getResultSize() {
        return size;
    }

    public void setResultSize(Integer size) {
        this.size = size;
    }


    /**
     * 总条数
     */
    public Integer getResultTotal() {
        return total;
    }

    public void setResultTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg +
                "', data=" + data +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                '}';
    }
}
