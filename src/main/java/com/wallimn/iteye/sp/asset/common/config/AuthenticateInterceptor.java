package com.wallimn.iteye.sp.asset.common.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 授权拦截器。用于检验授权情况
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:55:10<br>
 *
 */
public class AuthenticateInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticateInterceptor.class);
    

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //String url = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getRequestURI(); // 请求的路径
        //log.debug(url);
    	String referer = request.getHeader("Referer");
    	if(referer==null){
    		log.error("Referer为空，请求地址：{}",request.getRequestURI());
    	}
        if (request.getRequestURI().startsWith("/api/")) {
            return true; // REST接口放行
        }
        else{
        	String uri = request.getRequestURI();
        	//具体的处理逻辑
        	log.debug("uri={}",uri);
        }

//        Long userId = (Long) request.getSession().getAttribute("currentUser");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}