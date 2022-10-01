package com.easy.trip.common.exception;

import com.easy.trip.common.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Steven
 * @date 2022年09月25日 15:25
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultDTO systemInternalException(Exception e) {
        log.error("system internal error :{}", e);
        return ResultDTO.failure("System internal error ,Please contact admin ...");
    }
}
