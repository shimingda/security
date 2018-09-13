package com.dome.utils;

import com.dome.entity.Result;

public class ResultUtil {
    public static Object success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return (Result) success(null);
    }

    public static Object error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
