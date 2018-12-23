package com.wallimn.iteye.sp.asset.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 分页配置文件
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月22日 下午9:46:48<br>
 *
 */
@Configuration
@ConditionalOnClass(PageList.class)
public class PageListSerializationConfig {
    @Bean
    public Module pageListModule() {
        SimpleModule module = new SimpleModule("PageListModule");
        module.addSerializer(PageList.class, new PageListSerializer());
        return module;
    }
}
