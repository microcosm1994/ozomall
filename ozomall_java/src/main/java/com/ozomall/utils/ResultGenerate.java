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
        result.setResultCode(SUCCESS_CODE);
        result.setResultMsg("成功");
        return result;
    }

    public static Result genSuccessResult(String msg) {
        Result result = new Result();
        result.setResultCode(SUCCESS_CODE);
        result.setResultMsg(msg);
        return result;
    }

    public static <T> Result<T> genSuccessResult(T data, int page, int size, int total) {
        Result result = new Result();
        result.setResultCode(SUCCESS_CODE);
        result.setResultMsg("成功");
        result.setResultData(data);
        result.setResultPage(page);
        result.setResultSize(size);
        result.setResultTotal(total);
        return result;
    }

}
