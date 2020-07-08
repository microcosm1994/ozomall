package com.ozomall.utils;

import com.ozomall.entity.Result;

public class ResultGenerate {
    private static Integer ERROR_CODE = 0;
    private static Integer SUCCESS_CODE = 1;

    /**
     * 返回成功
     */
    public static Result genSuccessResult() {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setMsg("成功");
        return result;
    }

    public static Result genSuccessResult(String msg) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> genSuccessResult(T data) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setMsg("成功");
        result.setData(data);
        return result;
    }

    public static Result genErroResult(String msg) {
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMsg(msg);
        return result;
    }

}
