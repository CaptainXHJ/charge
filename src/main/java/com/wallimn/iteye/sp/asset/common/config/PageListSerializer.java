package com.wallimn.iteye.sp.asset.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 修正原插件中PageListJsonSerializer未加载Spring Boot全局配置（如：时间格式等）的问题
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:53:26<br>
 *
 */
@SuppressWarnings("rawtypes")
class PageListSerializer extends JsonSerializer<PageList> {
    @SuppressWarnings("unchecked")
	@Override
    public void serialize(PageList value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        Map<String,Object> objectMap = new HashMap<String,Object>();
        Paginator paginator = value.getPaginator();
        objectMap.put("totalCount", paginator.getTotalCount());
        objectMap.put("totalPages", paginator.getTotalPages());
        objectMap.put("page", paginator.getPage());
        objectMap.put("limit", paginator.getLimit());
        objectMap.put("items" , new ArrayList(value));
        objectMap.put("startRow", paginator.getStartRow());
        objectMap.put("endRow", paginator.getEndRow());
        objectMap.put("offset", paginator.getOffset());
        objectMap.put("slider", paginator.getSlider());
        objectMap.put("prePage", paginator.getPrePage());
        objectMap.put("nextPage", paginator.getNextPage());
        objectMap.put("firstPage", paginator.isFirstPage());
        objectMap.put("hasNextPage", paginator.isHasNextPage());
        objectMap.put("hasPrePage", paginator.isHasPrePage());
        objectMap.put("lastPage", paginator.isLastPage());
        jgen.writeObject(objectMap);
    }
}