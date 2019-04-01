package me.maxct.asset.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.maxct.asset.dto.Msg;

/**
 * @author imaxct
 * 2019-04-01 12:13
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    private static final String LOGGER_NAME = "asset-exception";
    private static final Logger LOGGER      = LoggerFactory.getLogger(LOGGER_NAME);

    @ExceptionHandler(IllegalArgumentException.class)
    public Msg handleIllegalArgumentException(IllegalArgumentException e) {
        return Msg.err(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Msg handleOtherException(Exception e) {
        LOGGER.error("other exception", e);
        return Msg.err("系统错误");
    }
}
