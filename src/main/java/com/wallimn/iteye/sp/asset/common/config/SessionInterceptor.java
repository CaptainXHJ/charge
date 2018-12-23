package com.wallimn.iteye.sp.asset.common.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 登录拦截器
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:54:51<br>
 *
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {
//    @Autowired
//    private UserRemoteService userRemoteService;
    private static final Logger log = LoggerFactory.getLogger(SessionInterceptor.class);

    /**
     * preHandle：预处理回调方法，实现处理器的预处理（如登录检查），第三个参数为响应的处理器（如我们上一章的Controller实现）；
     * 返回值：true表示继续流程（如调用下一个拦截器或处理器）；
     * false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginedUser = request.getSession().getAttribute(GlobalConst.loginedUserKey);
        if (loginedUser == null) {
        	//String uri = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getRequestURI(); // 请求的路径
        	//log.debug(uri);
        	log.error("用户未登录！请求受限资源：{}",request.getRequestURI());
        	log.error("处置器：{}",handler.toString());
            //throw new HServiceException(StatusCode.NOTLOGINED);
        }
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
