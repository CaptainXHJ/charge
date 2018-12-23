package com.wallimn.iteye.sp.asset.bus.ijpay.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wallimn.iteye.sp.asset.bus.ijpay.interceptor.AliPayInterceptor;
import com.wallimn.iteye.sp.asset.bus.ijpay.interceptor.CharacterEncodInterceptor;
import com.wallimn.iteye.sp.asset.bus.ijpay.interceptor.WxPayInterceptor;


/**
 * 配置拦截器，应对不同的支付
 * @author wallimn，2018年9月22日 下午1:44:29
 *
 */
@Configuration
public class IJPayWebMvcConfigurerAdapter implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
    	registry.addInterceptor(new CharacterEncodInterceptor()).addPathPatterns("/api/01/unionpay/**");
        registry.addInterceptor(new AliPayInterceptor()).addPathPatterns("/api/01/alipay/**");
        registry.addInterceptor(new WxPayInterceptor()).addPathPatterns("/api/01/wxpay/**","/wxsubpay/**");
        //super.addInterceptors(registry);
    }
}

