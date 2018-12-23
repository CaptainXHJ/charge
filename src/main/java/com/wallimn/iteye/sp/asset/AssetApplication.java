package com.wallimn.iteye.sp.asset;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBoot2.0启动程序
 * @author wallimn，http://wallimn.iteye.com
 *
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling//启用计划任务
@EnableTransactionManagement//启用事务管理
public class AssetApplication extends SpringBootServletInitializer {
	
	/**
	 * 用于支持打包成WAR，部署到tomcat中。
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AssetApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AssetApplication.class, args);
	}
	//如果没有使用默认值80
    @Value("${http.port:80}")
    Integer httpPort;

    //正常启用的https端口 如443
    @Value("${server.port}")
    Integer httpsPort;

    // springboot2 写法
    @Bean
    @ConditionalOnProperty(name="condition.http2https",havingValue="true", matchIfMissing=false)
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    @ConditionalOnProperty(name="condition.http2https",havingValue="true", matchIfMissing=false)
    public Connector httpConnector() {
    	System.out.println("启用http转https协议，http端口："+this.httpPort+"，https端口："+this.httpsPort);
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(httpPort);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(httpsPort);
        return connector;
    }}
