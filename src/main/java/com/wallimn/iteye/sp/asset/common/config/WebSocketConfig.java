package com.wallimn.iteye.sp.asset.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/*
 * WebSocket配置文件
 * 使用websocket时要启用这个注释
 */
//@Configuration
@SuppressWarnings("unused")
public class WebSocketConfig {
	@Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
        return new ServerEndpointExporter();  
    } 
}
