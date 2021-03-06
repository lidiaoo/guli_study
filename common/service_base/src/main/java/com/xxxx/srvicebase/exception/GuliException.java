package com.xxxx.srvicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {
    private Integer resultCode = 500;
    private String msg = "自定义异常";
}
