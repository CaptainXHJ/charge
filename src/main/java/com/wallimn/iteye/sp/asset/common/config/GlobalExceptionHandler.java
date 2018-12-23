package com.wallimn.iteye.sp.asset.common.config;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wallimn.iteye.sp.asset.common.exception.ExceptionDetail;
import com.wallimn.iteye.sp.asset.common.exception.ServiceException;
import com.wallimn.iteye.sp.asset.common.exception.StatusCode;

/**
 * 全局异常处理
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:54:00<br>
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    

    @SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("全局异常处理！");
        e.printStackTrace();
        if (e instanceof ServiceException) {
            LOGGER.warn(e.getMessage());
        } else {
            LOGGER.error(e.getMessage(), e);
        }

        String uri = request.getRequestURI();
        String acceptHeader = request.getHeader("Accept");
        if ((!StringUtils.isEmpty(acceptHeader) && acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE))
        		||(uri!=null && uri.contains("/api/"))) {
        	//后一种情况代表通过地址栏手工输入生意人地址
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            if (e instanceof ServiceException) {
                return ResponseEntity.status(((ServiceException) e).getCode()).body(((ServiceException) e).getMsg());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }else {
            String path = "/public/error.html";
            if (e instanceof ServiceException && ((ServiceException) e).getCode() == StatusCode.NOTLOGINED.getCode()) {
                path = "/public/login.html";
            }
            try {

            	//将异常放入Sessioin
            	ExceptionDetail ed = new ExceptionDetail();
            	ed.setMsg(e.getMessage());
            	ed.setUri(uri);
            	if(e instanceof ServiceException){
            		ed.setCode(((ServiceException)e).getCode());
            	}
            	request.getSession().setAttribute(GlobalConst.globalExceptionKey, ed);
                response.sendRedirect(path);
            } catch (IOException ex) {
                ex.printStackTrace();
                LOGGER.error(ex.getMessage(), e);
            }
        }
        return null;
    }
}