package org.wiki.controller;

import org.wiki.entity.ResponseData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class baseException {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseData error(Exception e){
        e.printStackTrace();
        System.out.println("diaoyong");
        return new ResponseData(500,e.getMessage());
    }
}
