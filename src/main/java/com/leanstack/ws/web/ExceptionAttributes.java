package com.leanstack.ws.web;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ExceptionAttributes {


    public Map<String,Object> getExceptionAttributes(Exception exception, HttpServletRequest request, HttpStatus internalServerError);

}
