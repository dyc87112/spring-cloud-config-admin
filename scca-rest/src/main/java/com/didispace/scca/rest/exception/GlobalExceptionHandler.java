package com.didispace.scca.rest.exception;

import com.didispace.scca.rest.dto.base.WebResp;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public WebResp<String> handleServiceException(ServiceException exception){
        return WebResp.serviceError(exception.getMessage());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    public WebResp<String> handleException(Exception exception){
        return WebResp.error(exception.getMessage());
    }

}
