package com.xxxx.srvicebase.exceptionhandler;

import com.xxxx.commonutils.R;
import com.xxxx.srvicebase.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public R exception(Exception e) {
        e.printStackTrace();
        return R.error()
                .message("执行了全局异常类: " + e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return R.error()
                .message("非法请求: " + e.getMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    public R arithmeticException(ArithmeticException e) {
        e.printStackTrace();
        return R.error()
                .message("数学运算异常: " + e.getMessage());
    }

    @ExceptionHandler(GuliException.class)
    public R guliException(GuliException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        Integer resultCode = e.getResultCode();
        String msg = e.getMsg();
        return R.error()
                .code(resultCode)
                .message(msg);
    }
}
