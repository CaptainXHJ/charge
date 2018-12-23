package com.wallimn.iteye.sp.asset.common.config;

import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.miemiedev.mybatis.paginator.CleanupMybatisPaginatorListener;

/**
 * 起用登录拦截器、权限拦截器
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:52:19<br>
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private static Logger log = LoggerFactory.getLogger(WebConfig.class);

    //这个原来在WebConfig中，转移到这个里面。
    @Bean
    @ConditionalOnClass(CleanupMybatisPaginatorListener.class)
    public ServletContextListener cleanupMybatisPaginatorListener() {
        return new CleanupMybatisPaginatorListener();
    }
    
    @Value("${cors.allowOrigin.url:anyValue}")
    private String corsAllowOriginUrl;
    
    
    /**
     * 允许跨域
     * 这个似乎没有起作用，很奇怪
     * @return
     */
    @ConditionalOnProperty(name="cors.allowOrigin.enable", havingValue="true", matchIfMissing=false)
    @Bean
    public CorsFilter corsFilter() {
      final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
      final CorsConfiguration corsConfiguration = new CorsConfiguration();
      corsConfiguration.setAllowCredentials(true);
      corsConfiguration.addAllowedOrigin("*");
      corsConfiguration.addAllowedHeader("*");
      corsConfiguration.addAllowedMethod("*");
      log.debug("启用跨域，允许域名：{}",corsAllowOriginUrl);
      urlBasedCorsConfigurationSource.registerCorsConfiguration(corsAllowOriginUrl, corsConfiguration);
      return new CorsFilter(urlBasedCorsConfigurationSource);
    }
   
    @Bean
    public SessionInterceptor sessionInterceptor() {
    	log.debug("初始化SessionInterceptor。");
        return new SessionInterceptor();
    }

    @Bean
    public AuthenticateInterceptor authenticateInterceptor() {
    	log.debug("初始化AuthenticateInterceptor。");
        return new AuthenticateInterceptor();
    }

    
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	//如果不符合SpringBoot默认设置的资源路径，可以使用下面配置设置。
    	//registry.addResourceHandler("/css/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/css/");
	}
    
    /**
     * 固定的要排除的项目
     */
    private static String[] excludeItems=new String[]{
            "/res/**",															//静态资源
    		"/login","/index","/","public/login.html","public/help.html",							//登录页面及其便捷映射
    		"/error","/public/error.html",										//错误页面及其映射
            "/media/**", 
            "/api/system/login", "/api/system/logout", 
            "/api/system/author","/api/exception/**",							//不需要拦截的API
            "/api/wx/**",														//不需要拦截的API
            "/api/math/**",														//不需要拦截的API
            "/api/inform/**",														//不需要拦截的API
            "/third/**",														//第三方登录用
            "/example/**",														//示例用
            "/api/example/**",													//示例用
            "/public/*.html"													//不需拦截的公共页面
    		};

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeItems);
        registry.addInterceptor(authenticateInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeItems);
    }

}
