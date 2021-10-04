package com.faq.javacustomerserver.throwable;

import com.faq.javacustomerserver.utils.Code;
import com.faq.javacustomerserver.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
class ThrowableController {
    private final Logger logger = LoggerFactory.getLogger(ThrowableController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<String> throwException(Exception ex ,WebRequest request ,HttpServletResponse response) {
        logger.error(ex.getMessage());
        return new ResponseResult<>(Code.EXCEPTION, ex.getMessage());
    }
}