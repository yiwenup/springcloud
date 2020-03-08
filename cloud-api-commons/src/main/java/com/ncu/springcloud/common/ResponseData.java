package com.ncu.springcloud.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {
    private Integer code;
    private String message;
    private T data;

    public ResponseData(Integer code, String message) {
        this(code, message, null);
    }
}
