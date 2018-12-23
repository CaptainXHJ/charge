package com.wallimn.iteye.sp.asset.common.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 切面技术，主要用于输出调试信息
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:51:35<br>
 *
 */
@Component
@Aspect
public class WebRequestAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebRequestAspect.class);
    private ThreadLocal<Long> beginTime = new ThreadLocal<Long>();
    @Autowired
    private ObjectMapper objectMapper;
    
    
    @Autowired
    private GlobalConfig globalConfig;

    //com.wallimn.arch.system.controller.DictionController
    @Pointcut("execution(public * com.wallimn.iteye.sp.asset.*.*.controller..*.*(..))")
    public void webRequest() {
    }

    @Before("webRequest()")
    public void doBefore(JoinPoint joinPoint) {
        beginTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        //LOGGER.info("请求URL：{}", request.getRequestURL().toString());
        LOGGER.info("请求URI：{}，参数：{}，方法：{}", request.getRequestURI(), request.getQueryString(),request.getMethod());

        List<String> params = new ArrayList<>();
        for (Object param : joinPoint.getArgs()) {
            if (param != null) { // 参数不为空
                if (param.toString().contains("com.wallimn.")) {
                    try {
                        params.add(objectMapper.writeValueAsString(param));
                    } catch (JsonProcessingException e) {
                        LOGGER.error(e.getMessage());
                    }
                } else {
                    params.add(param.toString());
                }
            } else {
                params.add(null);
            }
        }

        LOGGER.info("参数：{}", params);
    }

    @After("webRequest()")
    public void doAfter() {
    	long span = System.currentTimeMillis() - beginTime.get();
    	if(span<this.globalConfig.getRequestThreshold()){
    		LOGGER.info("请求耗时：{}ms", span);
    	}
    	else{
    		LOGGER.warn("请求耗时：{}ms", span);
    	}
    }

}
