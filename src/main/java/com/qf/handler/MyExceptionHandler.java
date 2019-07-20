package com.qf.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//@Component
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String error(Exception ex){
        ex.printStackTrace();
        return "error";
    }
}
