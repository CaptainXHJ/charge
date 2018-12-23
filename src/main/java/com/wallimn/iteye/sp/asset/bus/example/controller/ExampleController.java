package com.wallimn.iteye.sp.asset.bus.example.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.wallimn.iteye.sp.asset.bus.example.model.Example;
import com.wallimn.iteye.sp.asset.bus.example.service.ExampleService;
import com.wallimn.iteye.sp.asset.common.config.GlobalConfig;
import com.wallimn.iteye.sp.asset.common.config.GlobalConst;
import com.wallimn.iteye.sp.asset.common.controller.BaseController;

/**
 * 包含增、删、改、查的示例Controller
 * 
 * @author Lenovo
 *
 */
@RestController
@RequestMapping("/api/example")
public class ExampleController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ExampleController.class);

	@Autowired
	private ExampleService exampleService;

	@Autowired
	private GlobalConfig globalConfig;

	@RequestMapping("/author")
	public String getAuthor() {
		log.debug(GlobalConst.author);
		return GlobalConst.author;
	}

	@RequestMapping(value = "/rows", method = RequestMethod.GET)
	public PageList<Example> selectRows(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "sortKey", required = false) String sortKey,
			@RequestParam(value = "sortDir", required = false) String sortDir

	) {

		Example example = new Example();
		example.setName(keyword);

		if (page == null || page <= 0)
			page = 1;
		if (pageSize == null || pageSize <= 0)
			pageSize = globalConfig.getDefaultPageSize();
		String sortString = this.getOrderCondition(sortKey, sortDir, "id.desc");
		PageBounds pageBounds = new PageBounds(page, pageSize, Order.formString(sortString));

		return (PageList<Example>) this.exampleService.selectEntityList(example, pageBounds);
	}

	@RequestMapping(value = "/row", method = RequestMethod.GET)
	public Example selectRow(@RequestParam(value = "id", required = true) String id) {

		return this.exampleService.selectEntityById(id);
	}

	@RequestMapping(value = "/row", method = RequestMethod.POST)
	public Example insertRow(@RequestBody Example example) {

		this.exampleService.insertEntity(example);
		return example;
	}

	@RequestMapping(value = "/row", method = RequestMethod.PUT)
	public Example updateRow(@RequestBody Example example) {

		this.exampleService.updateEntity(example);
		return example;
	}

	@RequestMapping(value = "/row", method = RequestMethod.DELETE)
	public String deleteRow(@RequestBody Map<String,String> map) {

		this.exampleService.deleteEntityById(map.get("id"));
		return this.getSuccessJson();
	}

	@RequestMapping(value = "/rows", method = RequestMethod.DELETE)
	public String deleteRows(@RequestBody Map<String,String[]> map) {

		String[] ids = map.get("ids");
		this.exampleService.deleteEntityByIds(ids);
		return this.getSuccessJson();
	}
}
