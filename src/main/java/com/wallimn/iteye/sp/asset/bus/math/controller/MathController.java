package com.wallimn.iteye.sp.asset.bus.math.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wallimn.iteye.sp.asset.bus.math.model.Score;
import com.wallimn.iteye.sp.asset.bus.math.model.Shushi;
import com.wallimn.iteye.sp.asset.bus.math.service.MathService;
import com.wallimn.iteye.sp.asset.common.controller.BaseController;
import com.wallimn.iteye.sp.asset.common.util.StringUtil;

@RestController
@RequestMapping("/api/math")
public class MathController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(MathController.class);
	@Autowired
	private StringUtil stringUtil;
	
	@Autowired
	private MathService mathService;
	
	@RequestMapping("/generate")
	List<Shushi> generateTest(@RequestParam(defaultValue="10",required=false)int count,@RequestParam(defaultValue="false") boolean save){
		String testId = this.stringUtil.getGuid();
		List<Shushi> list = this.mathService.getShushiTest(testId, count);
		if(save==true){
			this.mathService.insertShushiTest(list);
			log.debug("生成试卷：{}",testId);
		}
		return list;
	}
	
	@RequestMapping("/test")
	List<Shushi> getTest(@RequestParam(required=true)String testId){
		log.debug("请求参数：{}",testId);
		List<Shushi> list = this.mathService.selectShushi(testId);
		return list;
	}

	@RequestMapping("/ps")
	List<Score> getPersonScore(@RequestParam(required=true)String id){
		log.debug("请求参数：openid={}",id);
		Score entity = new Score();
		entity.setOpenid(id);
		List<Score> list = this.mathService.selectScore(entity);
		return list;
	}
	
	@RequestMapping("/ts")
	List<Score> getTestScore(@RequestParam(required=true)String id){
		log.debug("请求参数：testId={}",id);
		Score entity = new Score();
		entity.setTestId(id);
		List<Score> list = this.mathService.selectScore(entity);
		return list;
	}
		
	@RequestMapping(value="/score", method = RequestMethod.POST)
	String postScore(@RequestBody Score model){
		log.debug("提交成绩：{}",model.getNickName());
		this.mathService.insertScore(model);
		return this.getSuccessJson();
	}
	
}
